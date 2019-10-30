
package com.mycompany.callcenter.model;

import com.mycompany.callcenter.service.Dispatcher;

public interface CallListener {

   void attendCall(Call call, Dispatcher dispatcher);

}
