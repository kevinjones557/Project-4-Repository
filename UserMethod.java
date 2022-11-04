import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class UserMethod {
    public String[] getAvailableUser(){
        ArrayList<String> usernames = new ArrayList<>();
        //File rootDir = new File(getRootDir()); //using Destin's function
        //String[] allDirectories = rootDir.list();
        //for(String userDir : allDirectories) {
        //    File thatUserBlockedFile = new File(getRootDir() + "\" + userDir + "hasBlocked");
        //    BufferedReader bfr = new BufferReader(new FileReader(thatUserBlockedFile));
        //    String line;
        //    while((line = bfr.readLine())!= null) {
        //        if(line.equals(this.username) {
        //            continue;
        //        }
        //    }
        //    usernames.add(userDir);
        // }

        String[] availables = new String[usernames.size()];
        for(int i = 0; i < availables.length; i++) {
            availables[i] = usernames.get(i);
        }
        return availables;
    }

    public void blockUser(String username) {
        // String blockedFilePath = this.getUserDirectory + "this.username" + "hasBlocked";
        // File blockedFile = new File(blockedFilePath);
        // blockedFile.createNewFile();
        // PrintWriter pw = new PrintWriter(new FileWriter(blockedFile));
        // pw.write(username);
        // pw.println();
        // pw.flush();
        // pw.close();
    }

}
