package rwth.lab.android.mensaviewer.parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import rwth.lab.android.mensaviewer.model.DayPlan;
import rwth.lab.android.mensaviewer.model.Extra;
import rwth.lab.android.mensaviewer.model.Menu;
import rwth.lab.android.mensaviewer.model.WeekPlan;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class WeekPlanXmlPullParser {
    private InputStream inputStream;

    public WeekPlanXmlPullParser(InputStream inputStream) {
        this.inputStream = new XmlSignFilterStream(inputStream);
    }

    public WeekPlan parse() throws IOException, XmlPullParserException {
        return doParse();
    }

    /**
     * internal parsing implementation
     *
     * @return
     */
    private WeekPlan doParse() throws IOException, XmlPullParserException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            // parser.nextTag();

            return readWeekPlan(parser);
        } finally {
            inputStream.close();
        }
    }

    private WeekPlan readWeekPlan(XmlPullParser parser) throws XmlPullParserException, IOException {
        String priceNote = null;
        String additives = null;
        String mensa = null;
        WeekPlan weekPlan = new WeekPlan();
        String name = "";
        String attributeName = "";
        String attributeValue = "";

        List<DayPlan> dayPlans = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            name = parser.getName();
            if (parser.getAttributeCount() > 0) {
                attributeName = parser.getAttributeName(0);
                attributeValue = parser.getAttributeValue(0);
            }
            // Go through the whole document; If you detect H3 class="default-headline" accept it as
            //marker for a single day plan, try read a day plan if it fails, proceed looking for next marker
            if ("h3".equalsIgnoreCase(name) && attributeEqualsIfNotNull(attributeName, attributeValue, "class", "default-headline")) {
                try {
                    dayPlans.add(readDayPlan(parser));
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ("p".equalsIgnoreCase(name) && attributeEqualsIfNotNull(attributeName, attributeValue, "id", "price-note")) {
                priceNote = readTextAndSupText(parser, "p");
            } else if ("p".equalsIgnoreCase(name) && attributeEqualsIfNotNull(attributeName, attributeValue, "id", "additives")) {
                additives = readTextAndSupText(parser, "p");
            } else if ("h2".equalsIgnoreCase(name)) {
                mensa = readTextAndSupText(parser, "h2");
            }
        }
        weekPlan.setMensa(mensa);
        weekPlan.setAddtitives(additives);
        weekPlan.setPriceNote(priceNote);
        weekPlan.setDayList(dayPlans);
        return weekPlan;
    }

    /**
     * Method is called after H3 class="default-headline" is detected
     * Will loop down to discover a headline,
     * if nothing is found, null is returned.
     *
     * @param parser
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private DayPlan readDayPlan(XmlPullParser parser) throws XmlPullParserException, IOException, IllegalStateException {
        String header = null;
        List<Menu> menues;
        List<Extra> extras;
        String tagName;
        DayPlan dayPlan = new DayPlan();
        String attributeName = null;
        String attributeValue = null;

        int event;
        //state 1: headline of the day! should be  7 times here
        while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
            tagName = parser.getName();
//            if(event==XmlPullParser.END_TAG&&equalsIfNotNull(tagName,"div")){
//                int i=0;
//                break;
//            }

            if (parser.getAttributeCount() >= 2) {
                attributeName = parser.getAttributeName(1);
                attributeValue = parser.getAttributeValue(1);
            }

            if ("a".equalsIgnoreCase(tagName)) {
                if (event != XmlPullParser.END_TAG)
                    header = nextText(parser);
                dayPlan.setHeader(header);
            } else if ("div".equalsIgnoreCase(tagName) &&
                    attributeEqualsIfNotNull(attributeName, attributeValue, "class", "default-panel")) {
                menues = readMenues(parser);//might fail, then whole dayplan is skipped, see exception handling one level above
                extras = readExtras(parser);
                dayPlan.setMenues(menues);
                dayPlan.setExtras(extras);
                break;
            }
        }
        return dayPlan;
    }

    private String nextText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String text;
        int event = parser.next();
        if ((event == XmlPullParser.TEXT)) {
            text = parser.getText();
            return removeWhitespaceCharacters(text);//remove all multiple occurences of whitespace/escape characters
        } else {

        }
        return null;
    }

    private String removeWhitespaceCharacters(String text) {
        return text.replaceAll("\\s{2,}", "");
    }

    boolean attributeEqualsIfNotNull(String attributeName, String attributeValue, String name, String value) {
        return (attributeName != null &&
                attributeValue != null &&
                attributeName.equalsIgnoreCase(name) &&
                attributeValue.equalsIgnoreCase(value));
    }

    private void readAndStopBySpecifiedTable(XmlPullParser parser, String table, int depth) throws XmlPullParserException, IOException {
        while (parser.next() != XmlPullParser.END_DOCUMENT && depth > 0) {
            if ("table".equalsIgnoreCase(parser.getName()) &&
                    attributeEqualsIfNotNull(parser.getAttributeName(0), parser.getAttributeValue(0), "class", table))
                return;
            depth--;
        }
    }

    private List<Menu> readMenues(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Menu> menues = new ArrayList<Menu>();

        //state 2: default-panel
        readAndStopBySpecifiedTable(parser, "menues", 2);
        int event;
        //state 3: menues table
        while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            if (event == XmlPullParser.END_TAG && "table".equalsIgnoreCase(tagName)) {
                break;
            }//get single menues untill you detect end of table
            //if we are here we are inside the table

            if ("tr".equalsIgnoreCase(parser.getName())) {//single row=single menu
                menues.add(readSingleMenu(parser));
            }
        }

        return menues;
    }

    private Menu readSingleMenu(XmlPullParser parser) throws XmlPullParserException, IOException {
        Menu menu = new Menu();
        String tagName;
        int event;
        while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
            tagName = parser.getName();

            if (event == XmlPullParser.END_TAG && "tr".equalsIgnoreCase(tagName)) {
                break;
            }

            if (parser.getAttributeCount() > 0)
                if ("td".equalsIgnoreCase(parser.getName()) &&
                        attributeEqualsIfNotNull(parser.getAttributeName(0), parser.getAttributeValue(0), "class", "category")) {
                    menu.setCategory(readTextAndSupText(parser, "td"));
                } else if ("td".equalsIgnoreCase(parser.getName()) &&
                        attributeEqualsIfNotNull(parser.getAttributeName(0), parser.getAttributeValue(0), "class", "menue")) {
                    menu.setMenu(readTextAndSupText(parser, "td"));
                } else if ("td".equalsIgnoreCase(parser.getName()) &&
                        attributeEqualsIfNotNull(parser.getAttributeName(0), parser.getAttributeValue(0), "class", "price")) {
                    menu.setPrice(readTextAndSupText(parser, "td"));
                }
        }
        return menu;
    }

    /**
     * Read the text of a menu and terminates when terminationEndTag is reached.
     * To call when parser is in proper state allready beeing in the cell (td).
     *
     * @param parser
     * @param terminationEndTag
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private String readTextAndSupText(XmlPullParser parser, String terminationEndTag) throws XmlPullParserException, IOException {
        int event;
        StringBuilder mTextBuilder = new StringBuilder();
        boolean previousSup = false;
        while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.TEXT) {
                if (previousSup) //we are inside <sub> text, we should enclose additives in braccets
                    mTextBuilder.append("(").append(parser.getText()).append(")");
                else
                    mTextBuilder.append(parser.getText());
            } else if (event == XmlPullParser.END_TAG && terminationEndTag != null && terminationEndTag.equalsIgnoreCase(parser.getName())) {//termination condition
                break; //leave loop if left  cell
            } else if (event == XmlPullParser.START_TAG && "sup".equalsIgnoreCase(parser.getName())) {
                previousSup = true;
                continue;
            }

            previousSup = false;
        }
        return removeWhitespaceCharacters(mTextBuilder.toString());
    }

    private Extra readSingleExtra(XmlPullParser parser) throws XmlPullParserException, IOException {
        Extra extra = new Extra();
        String tagName;
        int event;
        while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
            tagName = parser.getName();
            if (event == XmlPullParser.END_TAG && "tr".equalsIgnoreCase(tagName)) {
                break;
            }
            if (parser.getAttributeCount() > 0)
                if ("td".equalsIgnoreCase(parser.getName()) &&
                        attributeEqualsIfNotNull(parser.getAttributeName(0), parser.getAttributeValue(0), "class", "category")) {
                    extra.setCategory(readTextAndSupText(parser, "td"));
                } else if ("td".equalsIgnoreCase(parser.getName()) &&
                        attributeEqualsIfNotNull(parser.getAttributeName(0), parser.getAttributeValue(0), "class", "extra")) {
                    extra.setExtra(readTextAndSupText(parser, "td"));
                }
        }
        return extra;
    }

    private List<Extra> readExtras(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Extra> extras = new ArrayList<Extra>();
        String tagName;

        readAndStopBySpecifiedTable(parser, "extras", 2);
        //state 4: extras table
        int event;
        while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
            tagName = parser.getName();
            //if we are here we are inside the table
            if (event == XmlPullParser.END_TAG && "table".equalsIgnoreCase(tagName)) {
                break;
            }//get single menues untill you detect end of table
            if ("tr".equalsIgnoreCase(tagName)) {//single row=single menu
                extras.add(readSingleExtra(parser));
            }
        }
        return extras;
    }

    /**
     * Helper method during development
     *
     * @return
     */
    public String getInputStreamString() {
        this.inputStream = inputStream;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}