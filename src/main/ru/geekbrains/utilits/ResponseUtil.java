package main.ru.geekbrains.utilits;

import main.ru.geekbrains.clientside.model.ResponseData;
import main.ru.geekbrains.clientside.model.ResponseStatusType;

public class ResponseUtil
{
    public static ResponseData createSuccessAddFileResponse(String msg)
    {
        ResponseData responseData = new ResponseData();
        responseData.setResponseStatus(ResponseStatusType.SUCCESSADDFILE);
        responseData.setMessage(msg);
        return responseData;
    }

    public static ResponseData createSuccessDeleteFileResponse(String msg)
    {
        ResponseData responseData = new ResponseData();
        responseData.setResponseStatus(ResponseStatusType.SUCCESSDELETEFILE);
        responseData.setMessage(msg);
        return responseData;
    }

    public static ResponseData createErrorResponse(String msg)
    {
        ResponseData responseData = new ResponseData();
        responseData.setResponseStatus(ResponseStatusType.ERROR);
        responseData.setMessage(msg);
        return responseData;
    }
    public static ResponseData createSuccessRenameFileResponse(String newFileName, String oldFileName, String newPath, String msg)
    {
        ResponseData responseData = new ResponseData();
        responseData.setResponseStatus(ResponseStatusType.SUCCESSRENAME);
        responseData.setCurrentFileName(newFileName);
        responseData.setPreviousFileName(oldFileName);
        responseData.setCurrentFilePathServer(newPath);
        responseData.setMessage(msg);
        return responseData;
    }

}
