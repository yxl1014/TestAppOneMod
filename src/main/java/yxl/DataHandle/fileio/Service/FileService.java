package yxl.DataHandle.fileio.Service;

import org.springframework.stereotype.Service;
import yxl.UserAndTask.entity.T_result;

public interface FileService {
    boolean insertResult(T_result tResult);
}
