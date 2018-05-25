package net.jrtechs.www;

import java.util.ArrayList;
import java.util.List;

/**
 * Method for recursively defining HTML
 *
 * @author Jeffery Russell 5-24-18
 */
public class HtmlElement
{
    /** Curses, I can't name it class **/
    private List<String> clas;

    private List<String> id;

    private List<HtmlElement> elements;

    private String tag;

    private String contents;


    public HtmlElement(String html)
    {
        clas = new ArrayList<>();
        id = new ArrayList<>();
        elements = new ArrayList<>();

        ArrayList<String> tags = this.parseIntoTags(html);
        this.pruneTags(tags);

        if(tags.size() > 0)
        {

        }
    }


    private ArrayList<String> parseIntoTags(String html)
    {
        html = html.replace("\n", "");
        html = html.replace("\t", "");

        ArrayList<String> tags = new ArrayList<>();
        String temp = "";
        for(int i = 0; i < html.length(); i++)
        {
            if(html.substring(i, i+1).equals("<"))
            {
                if(temp.length() > 0)
                    tags.add(temp);
                temp = "<";
            }
            else if (html.substring(i, i+1).equals(">"))
            {
                temp += html.substring(i, i+ 1);
                tags.add(temp);
                temp = "";
            }
            else
            {
                temp+= html.substring(i, i+ 1);
            }
        }
        if(temp.length() > 0)
            tags.add(temp);
        return tags;
    }

    private void pruneTags(List<String> tags)
    {
        List<String> badStuff = new ArrayList<>();
        badStuff.add("<br>");
        badStuff.add("<!--");
        badStuff.add("-->");
        badStuff.add("<!");

        tags.forEach(t -> badStuff.forEach(b ->
        {
            if(t.contains(b))
                tags.remove(t);
        }));
    }


    public List<HtmlElement> getElementsWithClass(String clas)
    {
        List<HtmlElement> elems = new ArrayList<>();
        this.clas.stream().filter(s-> s.equals(clas)).forEach(s2->elems.add(this));
        this.elements.forEach(e -> elems.addAll(e.getElementsWithClass(clas)));
        return elems;
    }

    public List<HtmlElement> getElementsWithID(String ids)
    {
        List<HtmlElement> elems = new ArrayList<>();
        this.id.stream().filter(s-> s.equals(ids)).forEach(s2->elems.add(this));
        this.elements.forEach(e -> elems.addAll(e.getElementsWithID(ids)));
        return elems;
    }

    public List<HtmlElement> getElementsWithTag(String tag)
    {
        List<HtmlElement> elems = new ArrayList<>();
        if(this.tag.equals(tag))
            elems.add(this);

        this.elements.forEach(e -> elems.addAll(e.getElementsWithID(tag)));
        return elems;
    }

}
