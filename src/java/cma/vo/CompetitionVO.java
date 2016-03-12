/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 19:33:03
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.vo;

public class CompetitionVO
{
    private String firstName;
    private String lastName;

    public CompetitionVO()
    {
        firstName = "";
        lastName = "";
    }

    public CompetitionVO(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String toString()
    {
        return "{" + firstName + "," + lastName + "}";
    }
}
