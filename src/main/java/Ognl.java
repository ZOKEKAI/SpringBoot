import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * mybatis sql语句校验工具类
 *
 * @author zoke
 * @date 18-10-4 / 下午12:39
 */
public class Ognl {
    private Ognl() {
    }

    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null) {
            return true;
        } else {
            if (o instanceof String) {
                if (((String) o).length() == 0) {
                    return true;
                }
            } else if (o instanceof Collection) {
                if (((Collection) o).isEmpty()) {
                    return true;
                }
            } else if (o.getClass().isArray()) {
                if (Array.getLength(o) == 0) {
                    return true;
                }
            } else {
                if (!(o instanceof Map)) {
                    return false;
                }

                if (((Map) o).isEmpty()) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isNumber(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof Number) {
            return true;
        } else if (o instanceof String) {
            String str = (String) o;
            return str.length() == 0 ? false : (str.trim().length() == 0 ? false : StringUtils.isNumeric(str));
        } else {
            return false;
        }
    }

    public static boolean isBlank(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            String str = (String) o;
            return isBlank(str);
        } else {
            return false;
        }
    }

    public static boolean isBlank(String str) {
        if (str != null && str.length() != 0) {
            for (int i = 0; i < str.length(); ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNeedTenantId(Integer tenantId) {
        return tenantId == null ? false : tenantId.intValue() >= -1;
    }
}
