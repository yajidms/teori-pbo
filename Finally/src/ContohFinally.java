class ContohFinally {
  private static int j=0;

  public static void main(String[] args) {
    while(true)
    {
      try{
        System.out.println("pada saat i = " + j + ": ");
        if(j++ == 0){
          throw new Exception();
        }
        System.out.println("tidak terjadi eksepsi");
      }catch(Exception e){
        System.out.println("terdapat eksepsi");
      }finally{
        System.out.println("statement dalam blok finally");
        if(j == 3){
          break;
        }
      }
    }
  }
}
