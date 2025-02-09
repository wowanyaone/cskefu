/*
 * Copyright (C) 2017 优客服-多渠道客服系统
 * Modifications copyright (C) 2018-2022 Chatopera Inc, <https://www.chatopera.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chatopera.cc.controller.resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chatopera.cc.controller.Handler;
import com.chatopera.cc.model.Contacts;
import com.chatopera.cc.model.User;
import com.chatopera.cc.persistence.es.ContactsRepository;
import com.chatopera.cc.util.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ContactsResourceController extends Handler {

    @Autowired
    private ContactsRepository contactsRes;

    @RequestMapping("/res/contacts")
    @Menu(type = "res", subtype = "contacts")
    @ResponseBody
    public String add(ModelMap map, HttpServletRequest request, @Valid String q) {
        if (q == null) {
            q = "";
        }
        Page<Contacts> contactsList = contactsRes.findByCreaterAndSharesAndOrgi(super.getUser(request).getId(), super.getUser(request).getId(), super.getOrgi(request), false, q, new PageRequest(0, 10));

        JSONArray result = new JSONArray();
        for (Contacts contact : contactsList.getContent()) {
            JSONObject item = new JSONObject();
            item.put("id", contact.getId());
            item.put("text", contact.getName());
            result.add(item);
        }

        return result.toJSONString();
    }
}