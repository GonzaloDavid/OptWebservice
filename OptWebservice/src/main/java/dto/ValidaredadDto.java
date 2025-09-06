package dto;

public class ValidaredadDto {
   private String erroformat;
   private boolean existeerror;

    public String getErroformat() {
        return erroformat;
    }

    public boolean isExisteerror() {
        return existeerror;
    }

    public void setExisteerror(boolean existeerror) {
        this.existeerror = existeerror;
    }

    public void setErroformat(String erroformat) {
        this.erroformat = erroformat;
    }
}
