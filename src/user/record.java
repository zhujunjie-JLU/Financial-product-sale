package user;

public class record {
    String bankname="";
    String quarter="quarter";
    int own_net=0;
    int own_sd=0;
    int own_smartc=0;
    int own_specc=0;
    int consI_net=0;
    int consI_sd=0;
    int consI_smartc=0;
    int consI_specc=0;
    int consO_net=0;
    int consO_sd=0;
    int consO_smartc=0;
    int consO_specc=0;
    int bSum_net=0;
    int bSum_sd=0;
    int bSum_smartc=0;
    int bSum_specc=0;
    int d2own=0;
    int d2insure=0;
    int d2others=0;
    int d2sum=0;
    public void setBankname(String bankname){this.bankname=bankname;}
    public String getBankname(){return this.bankname;}
    public void setQuarter(String quarter){this.quarter=quarter;}
    public String getQuarter(){return this.quarter;}
    public void setOwn_net(int own_net){this.own_net=own_net;}
    public int getOwn_net(){return this.own_net;}
}

