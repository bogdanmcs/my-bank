package com.sw.banca.misc.fisc;

public class MetDecoder {
    private final String source;

    public MetDecoder(String source) {
        this.source = source;
    }

    public String getTextAttribute(){
        String text = source;
        if(text.startsWith("Text")){
            text = text.substring(text.indexOf("\"") + 1);
            text = text.substring(0, text.indexOf("\""));
        } else if(source.startsWith("ListViewSkin")){
            text = text.substring(text.indexOf("'") + 1);
            text = text.substring(0, text.indexOf("'"));
        } else {
            text = "UNEXPECTED_ERROR";
        }
        return text;
    }
}
