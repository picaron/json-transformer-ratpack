package com.appdirect.functions;

import static ratpack.jackson.Jackson.json;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.thisptr.jackson.jq.JsonQuery;
import ratpack.server.RatpackServer;

public class JsonTransformer {
	public static void main(String... args) throws Exception {
		RatpackServer.start(server -> server
			.registryOf(registry -> {
				if (Boolean.parseBoolean(System.getProperty("indent-output", "false"))) {
					registry.add(ObjectMapper.class, new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT));
				}
			})
			.handlers(chain ->
				chain.post("transform", ctx -> {
					ctx.parse(TransformRequest.class).then(request -> {
						JsonQuery jsonQuery = JsonQuery.compile(request.getQuery());
						List<JsonNode> result = jsonQuery.apply(request.getInput());
						ctx.render(json((result.size() == 1) ? result.get(0) : result));
					});
				})
			)
		);
	}
}
