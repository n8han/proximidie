package net.databinder.proximidie;

public interface OnErrorListener {

	public void onError(final String msg);

	public void onError(final Exception exc);

	public void onNotRoot();
}