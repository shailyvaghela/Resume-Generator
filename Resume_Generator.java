/* Name: Shaily Vaghela
 * Roll No.: 88
 * Enrollment number: 22002171310152
 * Branch: CST 
 * Date: 07/10/2023*/

/* no of classes: 4
 * no of variable: 8 var, 2 arraylists for user information*/

// Importing java io and java util packages
import java.io.*;
import java.util.*;

// declaring variables and getters, setters
class Resume {
    private String name;
    private String email;
    private String phoneNumber;
    private String dob;
    private String institute;
    private String yearofPassing;
    private String course;
    private Long experience;
    private ArrayList<String> skills;
    private ArrayList<String> project;

    //Constructors: empty and parametrized
    public Resume() {

    }

    public Resume(String name, String email, String phoneNumber, String dob, String institute, String yearofPassing,
            String course, Long experience, ArrayList<String> skills, ArrayList<String> project) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.institute = institute;
        this.yearofPassing = yearofPassing;
        this.course = course;
        this.experience = experience;
        this.skills = skills;
        this.project = project;
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getYearofPassing() {
        return yearofPassing;
    }

    public void setYearofPassing(String yearofPassing) {
        this.yearofPassing = yearofPassing;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getProject() {
        return project;
    }

    public void setProject(ArrayList<String> project) {
        this.project = project;
    }
}

//Methods: saveResumeToFile, getAllResumeNames, deletefile
class ResumeFileHandler {
    public String filePath;

    public ResumeFileHandler(String filePath) {
        this.filePath = filePath;
    }

    //Method which stores information in a new file 
    public void saveResumesToFile(Resume resume) throws IOException {
    String fileName = resume.getName() + ".txt";

    try  {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("Name: " + resume.getName());
        writer.newLine();
        writer.write("Email: " + resume.getEmail());
        writer.newLine();
        writer.write("Phone Number: " + resume.getPhoneNumber());
        writer.newLine();
        writer.write("Date of Birth: " + resume.getDob());
        writer.newLine();
        writer.write("Institute: " + resume.getInstitute());
        writer.newLine();
        writer.write("Year of Passing: " + resume.getYearofPassing());
        writer.newLine();
        writer.write("Course: " + resume.getCourse());
        writer.newLine();
        writer.write("Years of Experience: " + resume.getExperience());
        writer.newLine();
        writer.write("Skills:");
        for (String skill : resume.getSkills()) {
            writer.write(" - " + skill);
        }
        writer.newLine();
        writer.write("Projects:");
        for (String project : resume.getProject()) {
            writer.write(" - " + project);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Append the resume name to the list of resume names in the resume_list.txt file
        try (BufferedWriter listWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            listWriter.write(fileName);
            listWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method retrieves a list of all resume file names from "resume_list.txt."
    public ArrayList<String> getAllResumeNames() {
        ArrayList<String> resumeNames = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return resumeNames;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resumeNames.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resumeNames;
    }

    //Method which deletes file specifies by path
    void deletefile(String path) {
        try {
            File myObj = new File(path);
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the folder.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting the file: " + e.getMessage());
        }
    }
}

//Methods: addResumeToAL, deleteResumeFromAL, updateResume
class ResumeManager {
    public static ArrayList<Resume> resumes = new ArrayList<>();
    private ResumeFileHandler fileHandler;

    public ResumeManager(ResumeFileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    //Method which adds new resumes to an ArrayList 
    public void addResumeToAL(Resume resume) {
        resumes.add(resume);
        try {
            fileHandler.saveResumesToFile(resume);
        } catch (IOException e) {
        }
    }

    
    public void deleteResumeFromAL(String name) {
        Resume toRemove = null;
        for (Resume obj : resumes) {
            if (obj.getName().equalsIgnoreCase(name)) {
                toRemove = obj;
                break;
            }
        }

        if (toRemove != null) {
            resumes.remove(toRemove);
            ArrayList<String> resumeNames = fileHandler.getAllResumeNames();
            resumeNames.remove(name);
            updatefilePath(resumeNames);
        } else {
            System.out.println("No resume found with the given name.");
        }
    }

    private void updatefilePath(ArrayList<String> resumeNames) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileHandler.filePath))) {
            for (String name : resumeNames) {
                writer.write(name);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method which updates information in resume
    public void updateResume(String resumeName, Scanner sc) {
        String filePath = resumeName + ".txt";

        // Check if the resume file exists
        File resumeFile = new File(filePath);
        if (!resumeFile.exists()) {
            System.out.println("Resume not found.");
            return;
        }
        int updateChoice;
        do{
            System.out.println("Select what you want to update:");
            System.out.println("1. Skills");
            System.out.println("2. Projects");
            System.out.println("3. Exit update");
            updateChoice = sc.nextInt();
            sc.nextLine(); // Consume the newline character
            switch (updateChoice) {
                case 1:
                    System.out.println("Enter the updated skills:");
                    String updatedSkills = sc.nextLine();

                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        // Read the existing content from the file
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("Skills:")) {
                                // Replace the existing skills section with the updated skills
                                content.append("Skills:\n").append(updatedSkills).append("\n");
                            } else {
                                content.append(line).append("\n");
                            }
                        }

                        // Write the modified content back to the file
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                            writer.write(content.toString());
                            System.out.println("Skills updated successfully.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("Enter the updated projects:");
                    String updatedProjects = sc.nextLine();

                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        // Read the existing content from the file
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("Projects:")) {
                                // Replace the existing projects section with the updated projects
                                content.append("Projects:\n").append(updatedProjects).append("\n");
                            } else {
                                content.append(line).append("\n");
                            }
                        }

                        // Write the modified content back to the file
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                            writer.write(content.toString());
                            System.out.println("Projects updated successfully.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }while(updateChoice!=3);        
    }

}

//Main class
//Methods: addResume, deleteResume, removeNameFromList, dis_list
public class Resume_Generator {
    private static Scanner sc = new Scanner(System.in);
    private static ResumeManager resumeManager;
    private static ResumeFileHandler fileHandler;
    public static String rlist = "resume_list.txt";

    public static void main(String[] args) {
        fileHandler = new ResumeFileHandler(rlist);
        resumeManager = new ResumeManager(fileHandler);
        int choice;
        do {
            System.out.println("Choose an option:");
            System.out.println("1. Add a resume");
            System.out.println("2. Delete a resume");
            System.out.println("3. Display a resume");
            System.out.println("4. Update resume");
            System.out.println("5. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    dis_list(rlist);
                    addResume();
                    break;
                case 2:
                    dis_list(rlist);
                    deleteResume();
                    break;
                case 3:
                    dis_list(rlist);
                    System.out.println("Display particluar resume?(yes/no): ");
                    String dis = sc.next();
                    if (!dis.equalsIgnoreCase("yes")) {
                        break;
                    }
                    System.out.println("Enter the name of the person whose resume you want to display:");
                    String nameToDisplay = sc.next() + ".txt";
                    dis_list(nameToDisplay);
                    break;
                case 4:
                    dis_list(rlist);
                    System.out.println("Enter name of file you want to update");
                    String up = sc.next();
                    resumeManager.updateResume(up, sc);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 5);
        sc.close();
    }

    //Adds resume by taking inputs from user
    private static void addResume() {
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email address:");
        String email = sc.nextLine();
        System.out.print("Enter Phone Number:");
        String phoneNumber = sc.nextLine();
        System.out.print("Enter Date of Birth:");
        String dob = sc.nextLine();
        System.out.print("Enter university name :");
        String institute = sc.nextLine();
        System.out.print("Year of Passing: ");
        String yearOfPassing = sc.nextLine();
        System.out.print("Course: ");
        String course = sc.nextLine();
        System.out.print("Enter years of experience:");
        Long experience = sc.nextLong();

        ArrayList<String> skills = new ArrayList<>();
        System.out.println("Enter skills (or 'done' to exit):");
        while (true) {
            String skill = sc.nextLine();
            if (skill.equalsIgnoreCase("done")) {
                break;
            }
            skills.add(skill);
        }

        ArrayList<String> projects = new ArrayList<>();
        System.out.println("Enter projects (or 'done' to exit):");
        while (true) {
            String project = sc.nextLine();
            if (project.equalsIgnoreCase("done")) {
                break;
            }
            projects.add(project);
        }

        Resume resume1 = new Resume(name, email, phoneNumber, dob, institute, yearOfPassing, course, experience, skills,
                projects);
        resumeManager.addResumeToAL(resume1);
        System.out.println("Resume added successfully.");
    }

    //Deletes Resume 
    private static void deleteResume() {
        System.out.println("Enter name of person whose resume you want to delete: ");
        String delName = sc.nextLine();
        String delName2 = delName + ".txt";

        resumeManager.deleteResumeFromAL(delName2);

        // Remove the resume name from the resume_list.txt file
        removeNameFromList(delName);
        
        // Delete the resume file
        fileHandler.deletefile(delName2);

        System.out.println("Resume deleted successfully.");
    }

    private static void removeNameFromList(String name) {
        ArrayList<String> resumeNames = fileHandler.getAllResumeNames();

        // Remove the specified name from the list
        if (resumeNames.remove(name)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rlist))) {
                for (String resumeName : resumeNames) {
                    writer.write(resumeName);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    

    //Displays the list of available resumes
    private static void dis_list(String fname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            String line;
            System.out.println("Contents of " + fname + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
