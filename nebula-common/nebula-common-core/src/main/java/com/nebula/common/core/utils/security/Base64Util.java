/**
 * Copyright 2019 Yanzheng (https://github.com/micyo202). All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nebula.common.core.utils.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64Util
 * Base64工具类
 *
 * @author KING
 */
@Slf4j
public class Base64Util {
    /**
     * Base64 编码
     *
     * @param text 明文
     * @return 密文
     */
    public static String encode(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64 解码
     *
     * @param ciphertext 密文
     * @return 明文
     */
    public static String decode(String ciphertext) {
        if (StringUtils.isBlank(ciphertext)) {
            return null;
        }
        final byte[] decode = Base64.getDecoder().decode(ciphertext);
        return new String(decode, StandardCharsets.UTF_8);
    }
}
