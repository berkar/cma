/**
 * Created by IntelliJ IDEA.
 * User: Bertil
 * Date: 2006-feb-11
 * Time: 22:16:19
 * To change this template use File | Settings | File Templates.
 */
package cma.competition;

public class CompetitionManager {
    private static CompetitionManager ourInstance = new CompetitionManager();

    public static CompetitionManager getInstance() {
        return ourInstance;
    }

    private CompetitionManager() {
    }
}
