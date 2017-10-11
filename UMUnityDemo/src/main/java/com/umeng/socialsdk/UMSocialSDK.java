//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.umeng.socialsdk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.PopupWindow;


import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.utils.Log;
import com.umeng.weixin.umengwx.e;
import com.unity3d.player.*;

public class UMSocialSDK {
    static Activity mActivity;
   private static SHARE_MEDIA[] platformlist = {

	   SHARE_MEDIA.QQ,

	   SHARE_MEDIA.SINA,

	   SHARE_MEDIA.WEIXIN,


	   SHARE_MEDIA.WEIXIN_CIRCLE,

	   SHARE_MEDIA.QZONE,

	   SHARE_MEDIA.EMAIL,

	   SHARE_MEDIA.SMS,

	   SHARE_MEDIA.FACEBOOK,

	   SHARE_MEDIA.TWITTER,

	   SHARE_MEDIA.WEIXIN_FAVORITE,

	   SHARE_MEDIA.GOOGLEPLUS,

	   SHARE_MEDIA.RENREN,

	   SHARE_MEDIA.TENCENT,

	   SHARE_MEDIA.DOUBAN,

	   SHARE_MEDIA.FACEBOOK_MESSAGER,

	   SHARE_MEDIA.YIXIN,

	   SHARE_MEDIA.YIXIN_CIRCLE,

	   SHARE_MEDIA.INSTAGRAM,

	   SHARE_MEDIA.PINTEREST,

	   SHARE_MEDIA.EVERNOTE,

	   SHARE_MEDIA.POCKET,

	   SHARE_MEDIA.LINKEDIN,

	   SHARE_MEDIA.FOURSQUARE,

	   SHARE_MEDIA.YNOTE,

	   SHARE_MEDIA.WHATSAPP,

	   SHARE_MEDIA.LINE,

	   SHARE_MEDIA.FLICKR,

	   SHARE_MEDIA.TUMBLR,

	   SHARE_MEDIA.ALIPAY,

	   SHARE_MEDIA.KAKAO,

	   SHARE_MEDIA.DROPBOX,

	   SHARE_MEDIA.VKONTAKTE,

	   SHARE_MEDIA.DINGTALK,

	   SHARE_MEDIA.MORE,

   };
 
    private static UMAuthListener mAuthListener;
    private static UMShareListener mShareListener;
    private static ShareBoardConfig config = new ShareBoardConfig();
    static {
      
        mActivity = UnityPlayer.currentActivity;
      
        checkActivity();
        
       
        
    }

    public UMSocialSDK() {
    }

  



  
    
  

    public static void authorize(int platform, final AuthListener listener) {
        checkActivity();
       
       UMShareAPI.get(mActivity).getPlatformInfo(mActivity, platformlist[platform], new UMAuthListener() {
    	   @Override
           public void onStart(SHARE_MEDIA platform) {
              
           }
		@Override
		public void onError(final SHARE_MEDIA arg0, int arg1, final Throwable arg2) {
			// TODO Auto-generated method stub
	
			
			runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onAuth(getplatform(arg0), 0,"error",arg2.getMessage());
				}
			});
		}
		
		@Override
		public void onComplete(final SHARE_MEDIA arg0, int arg1, final Map<String, String> arg2) {
			// TODO Auto-generated method stub
	runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String keys = "";
					String values = "" ;
					int i = 0;
					for (String key :arg2.keySet()) {
						i++;

						keys = keys+key;
						if (i<=(arg2.keySet().size()-1)) {
							keys=keys+",";
						}
					}
					int j = 0;
					for (String value :arg2.values()) {
						j++;
						if (value.contains(",")){
							value = value.replace(","," ");
						}
						values = values+value;
						if (j<=(arg2.values().size()-1)) {
							values=values+",";
						}
					}
				
					Log.e("xxxxxx keys="+keys);
					listener.onAuth(getplatform(arg0), 200, keys,values);
				}
			});
		}
		
		@Override
		public void onCancel(final SHARE_MEDIA arg0, int arg1) {
			// TODO Auto-generated method stub
	runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onAuth(getplatform(arg0), -1,"error","cancel");
				}
			});
		}
	});
    }

   

    public static void deleteAuthorization(final int platform, final AuthListener listener) {
        checkActivity();
      UMShareAPI.get(mActivity).deleteOauth(mActivity, platformlist[platform], new UMAuthListener() {
    	   @Override
           public void onStart(SHARE_MEDIA platform) {
             
           }
		@Override
		public void onError(final SHARE_MEDIA arg0, int arg1, final Throwable arg2) {
			// TODO Auto-generated method stub
		//	listener.onAuth(var1, var2, var3, var4);
runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onAuth(getplatform(arg0), 0,"error",arg2.getMessage());
				}
			});
		}
		
		
		@Override
		public void onComplete(final SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {
			// TODO Auto-generated method stub
runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onAuth(getplatform(arg0), 200,"message","success");
				}
			});
		}
		
		
		@Override
		public void onCancel(final SHARE_MEDIA arg0, int arg1) {
			// TODO Auto-generated method stub
runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onAuth(getplatform(arg0), -1,"error","cancel");
				}
			});
		}
		
	});
    }

    public static boolean isAuthorized(int platform) {
        return UMShareAPI.get(mActivity).isAuthorize(mActivity, platformlist[platform]);
    }

  


   
    private static int getplatform(SHARE_MEDIA share_MEDIA){
    	for (int i = 0; i < platformlist.length; i++) {
			if (share_MEDIA == platformlist[i]) {
				return i;
			}
		}
    	return -1;
    }
  

   private static void runInUI(Runnable runnable) {
	mActivity.runOnUiThread(runnable);
}
    private static String getFileName(String fullname) {
        return !fullname.startsWith("assets/") && !fullname.startsWith("res/")?"":fullname.split("/")[1];
    }

public static void setDismissCallBack(final ShareBoardDismissListener listener){
	config.setOnDismissListener(new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            listener.onDismiss();
        }
    });
}
public static void openShareWithImagePath(int[] platforms,String text, String imagePath, String title,String targeturl,final ShareListener listener)
{
	SHARE_MEDIA[] share_medias = new SHARE_MEDIA[platforms.length];
	for (int i = 0; i < platforms.length; i++) {
		share_medias[i]= platformlist[platforms[i]];
	}
	ShareAction shareAction = new ShareAction(mActivity);
	if (TextUtils.isEmpty(targeturl)) {
		shareAction.setDisplayList(share_medias)
		.withText(text)
		.withMedia(parseShareImage(imagePath));
	}else {
		UMWeb web = new UMWeb(targeturl);
	        web.setTitle(title);
	        web.setThumb(parseShareImage(imagePath));
	        web.setDescription(text);
	        shareAction.setDisplayList(share_medias)
			.withText(text)
			.withMedia(web);
	}
shareAction
	.setCallback(new UMShareListener() {
		 @Override
         public void onStart(SHARE_MEDIA platform) {
           
         }
		@Override
		public void onResult(final SHARE_MEDIA arg0) {
			// TODO Auto-generated method stub
			Log.e("uuuuuusuccess");
			
			runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onShare(getplatform(arg0), 200, "success");
				}
			});
			
		}
		
		@Override
		public void onError(final SHARE_MEDIA arg0, final Throwable arg1) {
			// TODO Auto-generated method stub
			Log.e("uuuuuuerror");
QueuedWork.runInMain(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onShare(getplatform(arg0), -1, arg1.getMessage());
				}
			});
		//	listener.onShare(getplatform(arg0), -1, arg1.getMessage());
		}
		
		@Override
		public void onCancel(final SHARE_MEDIA arg0) {
			// TODO Auto-generated method stub
			Log.e("uuuuuuoncance");
			runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onShare(getplatform(arg0), 0, "cancel");
				}
			});
		//	listener.onShare(getplatform(arg0), 0, "cancel");

		}
	}).open(config);
}
    public static void directShare(String text, String imagePath, String title,String targeturl,int platform, final ShareListener listener) {
        checkActivity();
      //  setShareContent(text, imagePath);
        ShareAction shareAction = new ShareAction(mActivity);
    	if (TextUtils.isEmpty(targeturl)) {
    		shareAction.setPlatform(platformlist[platform])
    		.withText(text)
    		.withMedia(parseShareImage(imagePath));
    	}else {
    		UMWeb web = new UMWeb(targeturl);
    	        web.setTitle(title);
    	        web.setThumb(parseShareImage(imagePath));
    	        web.setDescription(text);
    	        shareAction.setPlatform(platformlist[platform])
    			.withText(text)
    			.withMedia(web);
    	}
    	shareAction
      .setCallback(new UMShareListener() {
    	   @Override
           public void onStart(SHARE_MEDIA platform) {
              
           }
		@Override
		public void onResult(final SHARE_MEDIA arg0) {
			// TODO Auto-generated method stub
			Log.e("uuuuuusuccess");
			
			runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onShare(getplatform(arg0), 200, "success");
				}
			});
			
		}
		
		@Override
		public void onError(final SHARE_MEDIA arg0, final Throwable arg1) {
			// TODO Auto-generated method stub
			UnityPlayer.UnitySendMessage("Example", "onShareback", "error"+arg1.getMessage());

QueuedWork.runInMain(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onShare(getplatform(arg0), -1, arg1.getMessage());
				}
			});
		//	listener.onShare(getplatform(arg0), -1, arg1.getMessage());
		}
		
		@Override
		public void onCancel(final SHARE_MEDIA arg0) {
			// TODO Auto-generated method stub
			UnityPlayer.UnitySendMessage("Example", "onShareback", "cancel");

			runInUI(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onShare(getplatform(arg0), 0, "cancel");
				}
			});
		//	listener.onShare(getplatform(arg0), 0, "cancel");

		}
	}).share();
    }

    public static void openLog(boolean flag) {
        Log.LOG = flag;
    }

   


//    private static SHARE_MEDIA getPlatform(int location) {
//        int length = mPlatformList.size();
//        return location >= 0 && location < length?(SHARE_MEDIA)mPlatformList.get(location):null;
//    }

    private static UMImage parseShareImage(String imgName) {
        if(TextUtils.isEmpty(imgName)) {
        
            return null;
        } else {
            UMImage shareImage = null;
            if(imgName.startsWith("http")) {
                shareImage = new UMImage(mActivity, imgName);
            } else if(imgName.startsWith("assets/")) {
                AssetManager imgFile = mActivity.getResources().getAssets();
                String index = getFileName(imgName);
                InputStream imgNameString = null;
                if(!TextUtils.isEmpty(index)) {
                    try {
                        imgNameString = imgFile.open(index);
                        shareImage = new UMImage(mActivity, BitmapFactory.decodeStream(imgNameString));
                        imgNameString.close();
                    } catch (IOException var14) {
                        var14.printStackTrace();
                    } finally {
                        if(imgNameString != null) {
                            try {
                                imgNameString.close();
                            } catch (IOException var13) {
                                var13.printStackTrace();
                            }
                        }

                    }
                }
            } else if(imgName.startsWith("res/")) {
                String imgFile1 = getFileName(imgName);
                if(!TextUtils.isEmpty(imgFile1)) {
                    int index1 = imgFile1.indexOf(".");
                    if(index1 > 0) {
                        String imgNameString1 = imgFile1.substring(0, index1);
                        int imgId = ResContainer.getResourceId(mActivity, "drawable", imgNameString1);
                        shareImage = new UMImage(mActivity, imgId);
                    } else {
                        Log.e("u3d", "### 请检查你传递的图片路径 : " + imgName);
                    }
                }
            } else {
                File imgFile2 = new File(imgName);
                if(!imgFile2.exists()) {
                    Log.e("u3d", "### 要分享的本地图片不存在");
                } else {
                    shareImage = new UMImage(mActivity, imgFile2);
                }
            }

            return shareImage;
        }
    }

   

  

 
    public static void updateOwnerActivity(Activity activity) {
        mActivity = activity;
    }

    private static void checkActivity() {
        assert mActivity != null : "在UMSocialSDK类中, mActivity为null.";

    }

//    private static int getPlatformId(SHARE_MEDIA platform) {
//        return mPlatformList.indexOf(platform);
//    }

//    private static void invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] args) {
//        if(object != null && !TextUtils.isEmpty(methodName)) {
//            try {
//                Method e = object.getClass().getMethod(methodName, parameterTypes);
//                Log.d("", "### 找到 " + methodName + ", obj : " + object);
//                e.invoke(object, args);
//            } catch (NoSuchMethodException var5) {
//                var5.printStackTrace();
//            } catch (IllegalAccessException var6) {
//                var6.printStackTrace();
//            } catch (IllegalArgumentException var7) {
//                var7.printStackTrace();
//            } catch (InvocationTargetException var8) {
//                var8.printStackTrace();
//            }
//
//        }
//    }

//    private static Object newHandlerInstance(String clzPath, Class<?>[] parameterTypes, Object[] args) {
//        if(clzPath == null) {
//            Log.d("", "#### newHandlerInstance , clzPath = " + clzPath);
//        }
//
//        try {
//            Class e = Class.forName(clzPath);
//            Constructor constructor = e.getConstructor(parameterTypes);
//            return constructor.newInstance(args);
//        } catch (NoSuchMethodException var5) {
//            var5.printStackTrace();
//        } catch (InstantiationException var6) {
//            var6.printStackTrace();
//        } catch (IllegalAccessException var7) {
//            var7.printStackTrace();
//        } catch (IllegalArgumentException var8) {
//            var8.printStackTrace();
//        } catch (InvocationTargetException var9) {
//            var9.printStackTrace();
//        } catch (ClassNotFoundException var10) {
//            var10.printStackTrace();
//        }
//
//        return null;
//    }

   

 
}
