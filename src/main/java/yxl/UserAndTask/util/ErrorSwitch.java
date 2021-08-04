package yxl.UserAndTask.util;

public class ErrorSwitch {
    public static String getValue(int ok) {
        String msg = "";
        switch (ok) {
            case 0:
                msg = "成功";
                break;
            case -1:
                msg = "失败";
                break;
            case 1:
                msg = "该用户已存在";
                break;
            case 2:
                msg = "用户账户余额不足";
                break;
            case 3:
                msg = "没有该任务";
                break;
            case 4:
                msg = "任务还不可以接受";
                break;
            default:
                msg = "服务器遇到未预测的错误！";
        }
        return msg;
    }
}
