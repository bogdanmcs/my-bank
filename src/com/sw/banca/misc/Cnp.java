package com.sw.banca.misc;

public class Cnp {
    private String cnpCode;

    public Cnp(String cnpCode){
        this.cnpCode = cnpCode;
    }

    public boolean isValid(){
        if(cnpCode.length() != 3){ // 13
            return false;
        }
        for(int i = 0; i < cnpCode.length(); i++){
            if(!Character.isDigit(cnpCode.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public String getCnpCode() {
        return cnpCode;
    }

    public void setCnpCode(String cnpCode) {
        this.cnpCode = cnpCode;
    }
}
