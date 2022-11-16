import java.io.File;

public class Placeholders {

    /** Recreates these files because they are needed to push to the repository
     so that the data directory doesn't disappear
     */

    public static void create() {
        try {
            File placeHolderOne = new File("data/buyers/placeholderbuyer");
            placeHolderOne.createNewFile();
            File placeHolderTwo = new File("data/sellers/placeholderseller");
            placeHolderTwo.createNewFile();
        } catch (Exception e) {
            System.out.println("Please manually create placeholder files for " +
                    "buyers and sellers!");
        }
    }

    /** Deletes these placeholder files so that they don't interfere with the
     program. They are needed for pushing to the repo because without them,
     the data directory disappears because it's empty */

    public static void delete() {
        File placeHolderOne = new File ("data/buyers/placeholderbuyer");
        placeHolderOne.delete();
        File placeHolderTwo = new File ("data/sellers/placeholderseller");
        placeHolderTwo.delete();
    }
}
