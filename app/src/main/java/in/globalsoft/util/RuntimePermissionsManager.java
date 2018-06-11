package in.globalsoft.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gionee yubo 2016-05-23 add for Runtime Permission
 *
 */
public class RuntimePermissionsManager {
    private static final String TAG = "RuntimePermissionsManager";
    
    public static final int REQUIRED_PERMISSIONS_REQUEST_CODE = 2000;
    
    private static final List<String> REQUIRED_PERMISSIONS = new ArrayList<String>();
    static {
        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        REQUIRED_PERMISSIONS.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        REQUIRED_PERMISSIONS.add(Manifest.permission.CAMERA);
    }
    
    public static boolean isPermissionCheckOpen() {
        return true;
    }
    
    public static boolean isBuildSysNeedRequiredPermissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
    
    public static boolean isRequestPermissionsCode(int requestCode) {
        return REQUIRED_PERMISSIONS_REQUEST_CODE == requestCode;
    }
    
    public static boolean hasNeedRequiredPermissions(Activity activity) {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (activity.checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public static void requestRequiredPermissions(Activity activity, int requestCode) {
        List<String> requiredPermissions = getNoGrantedPermissions(activity);
        if (requiredPermissions.isEmpty()) {
            return;
        }
        requestPermissions(activity, requiredPermissions, requestCode);
    }

    private static List<String> getNoGrantedPermissions(Activity activity) {
        List<String> noGrantedPermissions = new ArrayList<String>();
        for (String permission : REQUIRED_PERMISSIONS) {
            if (activity.checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                noGrantedPermissions.add(permission);
            }
        }
        return noGrantedPermissions;
    }

    private static void requestPermissions(Activity activity, List<String> requiredPermissions, int requestCode) {
        String[] permissions = requiredPermissions.toArray(new String[requiredPermissions.size()]);
        activity.requestPermissions(permissions, requestCode);
    }

    public static boolean hasDeniedPermissions(String[] permissions, int[] grantResults) {
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "hasDeniedPermissions permission:" + permissions[i] + ", grantResult:" + grantResults[i]);
                return true;
            }
        }
        return false;
    }
    
}
