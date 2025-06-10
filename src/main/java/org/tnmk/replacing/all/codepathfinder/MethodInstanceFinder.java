package org.tnmk.replacing.all.codepathfinder;

import java.io.*;
import java.util.*;

public class MethodInstanceFinder
{
    public static void main(String[] args)
    {
        String projectPath = "path/to/your/project"; // Replace with your project path
        String className = "MultiTenantConnectionProvider"; // Replace with your class name
        String methodName = "releaseConnection"; // Replace with your method name

        List<String> results = findMethodInstances(projectPath, className, methodName);
        results.forEach(System.out::println);
    }

    private static List<String> findMethodInstances(String projectPath, String className, String methodName)
    {
        List<String> results = new ArrayList<>();
        File projectDir = new File(projectPath);

        if (projectDir.exists() && projectDir.isDirectory())
        {
            searchFiles(projectDir, className, methodName, results);
        }

        return results;
    }

    private static void searchFiles(File dir, String className, String methodName, List<String> results)
    {
        for (File file : Objects.requireNonNull(dir.listFiles()))
        {
            if (file.isDirectory())
            {
                searchFiles(file, className, methodName, results);
            }
            else if (file.getName().endsWith(".java"))
            {
                searchFile(file, className, methodName, results);
            }
        }
    }

    private static void searchFile(File file, String className, String methodName, List<String> results)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            int lineNumber = 0;
            boolean classImported = false;

            while ((line = reader.readLine()) != null)
            {
                lineNumber++;

                // Check if the class is imported
                if (line.contains("import " + className + ";"))
                {
                    classImported = true;
                }

                // Check for method calls
                if (classImported && line.contains(methodName + "("))
                {
                    results.add("Found in: " + file.getPath() + " at line " + lineNumber);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
