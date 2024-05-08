/*
 * The MIT License
 *
 * Copyright 2024 samueladebowale.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cometbid.component.ut.jpa.pagination;

import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 *
 * @author samueladebowale
 */
@Log4j2
public class PagingFactory {

    private PagingFactory() {

    }

    /**
     *
     * @param map
     * @return
     */
    public static List<Order> createSortOrder(Map<String, Sort.Direction> map) {

        Set<Order> sortOrder = map.entrySet()
                .stream().filter(e -> e != null && StringUtils.isNotEmpty(e.getKey()))
                .map(e -> {
                    if (e.getValue() == null) {
                        e.setValue(Sort.Direction.ASC);
                    }
                    return e;
                })
                .map(entry -> new Order(entry.getValue(), entry.getKey()))
                .collect(Collectors.toSet());

        List<Order> orderList = new ArrayList<>();
        orderList.addAll(sortOrder);

        return orderList;
    }

    /**
     *
     * @param map
     * @return
     */
    public static Map<String, Sort.Direction> convertTo(Map<String, String> map) {

        return map.entrySet()
                .stream().filter(e -> e != null)
                .filter(PagingFactory::excludeFields)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> getSortDir(e.getValue()))
                );
    }

    private static boolean excludeFields(Map.Entry<String, String> e) {
        return !(e.getKey().equalsIgnoreCase("size")
                || e.getKey().equalsIgnoreCase("page"));
    }

    /**
     *
     * @param direction
     * @return
     */
    private static Sort.Direction getSortDir(String direction) {

        if (StringUtils.startsWithIgnoreCase(direction, "d")) {
            return Sort.Direction.DESC;
        } else {
            return Sort.Direction.ASC;
        }
    }

    /**
     *
     * @param map
     * @return
     */
    public static PagingModel createPagingModel(Map<String, String> map) {

        String page = map.getOrDefault("page", "");
        String size = map.getOrDefault("size", "");

        Integer pageNo = Ints.tryParse(page);
        Integer pageSize = Ints.tryParse(size);

        if (pageNo == null) {
            pageNo = PagingModel.DEFAULT.page();
        }

        if (pageSize == null) {
            pageSize = PagingModel.DEFAULT.size();
        }

        return PagingModel.of(pageNo, pageSize);
    }

}
