package com.proschoolonline.networkutils;

public enum HTTPResponseCacheProperties {
    NOPROPS(0),NOCACHE(1), MAXAGE0(2), ONLYIFCACHED(3), MAXSTALEIS(4);
    private int value;

    private HTTPResponseCacheProperties(int value) {
            this.value = value;
    }
    
    @Override
    public String toString() {
    	String strReturn = null;
         switch (this) {
           case NOPROPS:
        	   strReturn= null;
                break;
           case NOCACHE:
                strReturn = "no-cache";
                break;
           case MAXAGE0:
        	   strReturn = "max-age=0";
                break;
           case ONLYIFCACHED:
        	   strReturn = "only-if-cached";
           case MAXSTALEIS:
        	   strReturn = "max-stale=";
                
          }
    return strReturn;
   }
   
};
