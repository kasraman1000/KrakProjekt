public class Client {
    
    private String[] addressTest(String[] addresses){
        String[] resultArr = new String[addresses.length*2];
        for(int index = 0; index < addresses.length; index++){
            resultArr[index*2] = addresses[index];
            try {
                if(addresses[index].equals("")){
                    continue;
                }
                String[] singleIn = AddressParser.parseAddress(addresses[index]);
                String singleOut = singleIn[0];
                for(int innerIndex =1; innerIndex < singleIn.length; innerIndex++){
                    singleOut += "#" + singleIn[innerIndex];
                }
                resultArr[index*2+1] = singleOut; 
            } catch (IllegalArgumentException e) {
                resultArr[index*2+1] = "EXCEPTION"; 
            }
        }
        return resultArr;
    }
    
    
    public static void main(String[] args) {
        Client client = new Client();
        
    }
    
    public Client(){
        String[] adressArr;
        adressArr = TextIO.read("Test.txt");
        TextIO.write(addressTest(adressArr));
    }
    
}
