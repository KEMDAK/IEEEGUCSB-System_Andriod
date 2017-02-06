package org.ieeeguc.ieeeguc;

import org.json.JSONObject;

/**
 * An interface to control the flow of the Async code with the Sync code
 */
public interface HTTPResponse {
    /**
     * This method is called when the HTTP response is successfully received and the status code is 2xx
     * @param statusCode The status code of the HTTP response
     * @param body The body of the HTTP response
     */
    public void onSuccess(int statusCode, JSONObject body);

    /**
     * This method is called when the HTTP response is successfully received and the status code is of the following [3xx, 4xx, 5xx]
     * @param statusCode The status code of the HTTP response
     * @param body The body of the HTTP response
     */
    public  void onFailure(int statusCode, JSONObject body);
}
