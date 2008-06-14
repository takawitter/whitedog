package jp.whitedog.sample.autosessionpaint;

import jp.whitedog.AutoSessionManagementAspect;
import jp.whitedog.jgroups.JGroupsSession;

public aspect PaintSessionAspect extends AutoSessionManagementAspect{
	public PaintSessionAspect(){
		super(new JGroupsSession("hello"));
	}
}
