package com.appdirect.functions;

import static ratpack.jackson.Jackson.json;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import net.thisptr.jackson.jq.JsonQuery;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import ratpack.server.RatpackServer;

public class JsonTransformer {
	private static final Logger logger = LoggerFactory.getLogger(JsonTransformer.class);

	@SuppressWarnings("unchecked")
	public static void main(String... args) throws Exception {
		RatpackServer.start(server -> server
			.registryOf(registry -> {
				if (Boolean.parseBoolean(System.getProperty("indent-output", "false"))) {
					registry.add(ObjectMapper.class, new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT));
				}
				Cache<TransformRequest, Object> cache = Caffeine.newBuilder().maximumSize(10000).build();
				registry.add(cache);
			})
			.handlers(chain -> chain
				.post("transform", ctx -> {
					ctx.parse(TransformRequest.class).then(request -> {
						Cache<TransformRequest, Object> cache = ctx.get(Cache.class);
						ctx.render(cache.get(request, JsonTransformer::process));
					});
				})
			)
		);
	}

	private static Object process(TransformRequest request) {
		try {
			logger.info("Processing new result");
			JsonQuery jsonQuery = JsonQuery.compile(request.getQuery());
			List<JsonNode> result = jsonQuery.apply(request.getInput());
			return json((result.size() == 1) ? result.get(0) : result);
		} catch (JsonQueryException e) {
			return "{\"error\": \"" + e.getCause().getMessage().replaceAll("\"", "\\\"") + "\"}";
		}
	}
}
