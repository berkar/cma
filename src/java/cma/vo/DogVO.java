/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 19:33:03
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.vo;

public class DogVO
{
    private String name;
    private String breed;
    private String gender;

    public DogVO()
    {
        name = "";
        breed = "";
        gender = "";
    }

    public DogVO(String name, String breed, String gender)
    {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBreed()
    {
        return breed;
    }

    public void setBreed(String breed)
    {
        this.breed = breed;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String toString()
    {
        return "{" + name + "," + breed + "," + gender + "}";
    }
}
