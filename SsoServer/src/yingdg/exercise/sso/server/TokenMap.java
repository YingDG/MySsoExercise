package yingdg.exercise.sso.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yingdg on 2017/6/8.
 */
public class TokenMap {
    public static final Map<String, String> TICKET_AND_NAME = new ConcurrentHashMap<String, String>();
}
