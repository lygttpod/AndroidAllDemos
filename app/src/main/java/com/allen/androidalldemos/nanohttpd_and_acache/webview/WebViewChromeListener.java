package com.allen.androidalldemos.nanohttpd_and_acache.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

public class WebViewChromeListener extends WebChromeClient {
	protected WebView m_vWebView;
	protected Activity m_pActivity;

	public WebViewChromeListener(Activity activity, WebView vWebView) {
		m_vWebView = vWebView;
		m_pActivity = activity;
	}

	/**
	 * 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
	 */
	private android.content.DialogInterface.OnKeyListener m_pOnKeyListener = new android.content.DialogInterface.OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
			return true;
		}
	};

	@Override
	public void onCloseWindow(WebView window) {
		super.onCloseWindow(window);
	}

	@Override
	public boolean onCreateWindow(WebView view, boolean dialog,
			boolean userGesture, Message resultMsg) {
		return super.onCreateWindow(view, dialog, userGesture, resultMsg);
	}

	/**
	 * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////�?
	 * ?message=内容&type=1&button=启用,|取消,|&id=&
	 */
	@Override
	public boolean onJsAlert(WebView view, String url, String message,
			JsResult result) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				view.getContext());
		// builder.setTitle("测试alert对话�?)
		builder.setTitle("温馨提示").setMessage(message)
				.setPositiveButton("确定", null);
		// 不需要绑定按键事�?
		// 屏蔽keycode等于84之类的按�?
		builder.setOnKeyListener(m_pOnKeyListener);
		// 禁止响应按back键的事件
		builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();

		result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容�?
		return true;
	}

	@Override
	public boolean onJsBeforeUnload(WebView view, String url, String message,
			JsResult result) {
		return super.onJsBeforeUnload(view, url, message, result);
	}

	/**
	 * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////�?
	 * message约定：提示信息�?操作类型1华西启用栏目、栏目ID ?message=内容&type=1&button=启用&id=&
	 */
	@Override
	public boolean onJsConfirm(WebView view, String url, String message,
			final JsResult result) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				view.getContext());
		// builder.setTitle("测试confirm对话�?)
		builder.setTitle("温馨提示")
				.setMessage(message)
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}
						})
				.setNeutralButton("取消",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});
		builder.setOnCancelListener(new AlertDialog.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {

			}

		});
		// 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
		builder.setOnKeyListener(m_pOnKeyListener);
		// 禁止响应按back键的事件
		// builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();

		return true;
	}

	/**
	 * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////�?
	 * window.prompt('请输入您的域名地�?, '618119.com');
	 */
	@Override
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, final JsPromptResult result) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				view.getContext());
		// builder.setTitle("测试prompt对话�?).setMessage(message);
		builder.setTitle("温馨提示");
		builder.setMessage(message);
		final EditText et = new EditText(view.getContext());
		et.setSingleLine();
		et.setText(defaultValue);
		builder.setView(et);
		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm(et.getText().toString());
					}
				});
		builder.setNeutralButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				});

		builder.setOnKeyListener(m_pOnKeyListener);

		// 禁止响应按back键的事件
		// builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();

		return true;
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
		if (m_pActivity != null) {
			m_pActivity.setProgress(newProgress * 1000);
		}
	}

	@Override
	public void onReceivedIcon(WebView view, Bitmap icon) {
		super.onReceivedIcon(view, icon);
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
	}

	@Override
	public void onRequestFocus(WebView view) {
		super.onRequestFocus(view);
	}

}
