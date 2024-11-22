package services;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    String filePath = "src/files/audit.csv";
    private static AuditService auditService;

    public static AuditService getAuditService(){
        if(auditService == null)
            auditService = new AuditService();
        return auditService;
    }

    private AuditService(){
            File f = new File(filePath);
            try
            {
                if(f.createNewFile()) {
                    log("Create csv file");
                    System.out.println("Csv file created.");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }

    public void log(String action){
        if(new File(filePath).exists())
        {
            try(FileWriter fileWr = new FileWriter(filePath, true))
            {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
                fileWr.write(action + "," + timestamp + ";\n");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else
            System.out.println("File does not exist.");
    }
}
