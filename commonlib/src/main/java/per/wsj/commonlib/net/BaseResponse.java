package per.wsj.commonlib.net;

import com.google.gson.annotations.SerializedName;

/**
 */

public class BaseResponse<E> {

    @SerializedName("code")
    public String code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("detail")
    public E detail;
//    @SerializedName("object")
//    public E object;

}
