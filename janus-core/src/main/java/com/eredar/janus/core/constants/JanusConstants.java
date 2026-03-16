package com.eredar.janus.core.constants;

public class JanusConstants {

    /* ---------- 分支名称 start ---------- */
    public static final String PRIMARY = "primary"; // 主分支
    public static final String SECONDARY = "secondary"; // 次要分支
    /* ---------- 分支名称 end ---------- */

    /* ---------- 比对结果状态 start ---------- */
    // 比对成功
    public static final String SUCCESS = "success";
    // 2个分支运行成功，但是比对有差异
    public static final String DIFFERENT = "different";
    // 2个分支都报错
    public static final String ALL_ERROR = "all_error";
    // primary 分支报错
    public static final String PRIMARY_ERROR = "primary_error";
    // secondary 分支报错
    public static final String SECONDARY_ERROR = "secondary_error";
    /* ---------- 比对结果状态 end ---------- */
}
