package com.fleencorp.base.adapter.base;

import com.fleencorp.base.constant.base.EndpointBlock;

/**
 * Represents a base implementation of an endpoint block used in API requests.
 * This record encapsulates the value representing an endpoint block.
 * It implements the EndpointBlock interface.
 *
 * @param value The value representing the endpoint block
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public record BaseEndpointBlock(String value) implements EndpointBlock {
}

