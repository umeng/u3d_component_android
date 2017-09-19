//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.umeng.pushsdk;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.tag.TagManager;
import com.unity3d.player.UnityPlayer;

public class UMPushSDK {
    static Activity mActivity;
    private static final int ERROR= 0;
    private static final int SUCCESS= 200;
    private static AliasListener mAliasListener;
    private static RemainListener mRemainListener;
	private static TagsListener mTagsListener;
	 private static PushAgent mPushAgent;
    static {
      
        mActivity = UnityPlayer.currentActivity;
      
        checkActivity();
        
       
        
    }

    public UMPushSDK() {
    }
    private static void checkActivity() {
        assert mActivity != null : "在UMSocialSDK类中, mActivity为null.";
        mPushAgent = PushAgent.getInstance(mActivity);
    }




	


    public static void addTag(String tag, final RemainListener listener) {
		Log.e("xxxxxx","addTag");
		mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {
			@Override
			public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
				if (isSuccess) {
					listener.onRemain(SUCCESS, result.remain);	
				} else {
					listener.onRemain(ERROR, 0);	
				}
			}
		}, tag);
	}
	public static void deleteTag(String tag, final RemainListener listener) {
		mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
			@Override
			public void onMessage(boolean isSuccess, final ITagManager.Result result) {
				
				if (isSuccess) {
					listener.onRemain(SUCCESS, result.remain);	
				} else {
					listener.onRemain(ERROR, 0);	
				}
			}
		}, tag);
	}

	public static  void listTag(final TagsListener listener) {
		mPushAgent.getTagManager().getTags(new TagManager.TagListCallBack() {
			@Override
			public void onMessage(final boolean isSuccess, final List<String> result) {
				//runNativeCallback(new Runnable() {
				//    @Override
				//    public void run() {
				if (isSuccess) {
					if (result != null) {
						String r = "";
						for (int i = 0; i < result.size(); i++) {
							r = r+result.get(i)+",";
						}
						r.substring(0, r.length()-1);
					listener.onTag(r);
					} else {
						listener.onTag("");
					}
				} else {
					listener.onTag("");
				}

				//}
				//}
				//);

			}
		});
	}

	public static void addAlias(String alias, String aliasType,final AliasListener listener) {
		mPushAgent.addAlias(alias, aliasType, new UTrack.ICallBack() {
			@Override
			public void onMessage(final boolean isSuccess, final String message) {
				
				Log.e("xxxxxx","isuccess"+isSuccess);
				if (isSuccess) {
					listener.onAlias(SUCCESS);
				} else {
					listener.onAlias(ERROR);
				}


			}
		});
	}

	public static void addAliasType() {

	}

	public static void setAlias(String exclusiveAlias, String aliasType,final AliasListener listener) {
		mPushAgent.setAlias(exclusiveAlias, aliasType, new UTrack.ICallBack() {
			@Override
			public void onMessage(final boolean isSuccess, final String message) {

				if (isSuccess) {
					listener.onAlias(SUCCESS);
				} else {
					listener.onAlias(ERROR);
				}



			}
		});
	}

	public static void deleteAlias(String alias, String aliasType,final AliasListener listener) {
		mPushAgent.deleteAlias(alias, aliasType, new UTrack.ICallBack() {
			@Override
			public void onMessage(boolean isSuccess, String s) {
				if (isSuccess) {
					listener.onAlias(SUCCESS);
				} else {
					listener.onAlias(ERROR);
				}
			}
		});
	}

 
}
