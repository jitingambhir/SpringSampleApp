package io.spring.start.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import io.spring.start.domain.ChatSession;
import io.spring.start.repository.ChatSessionRepository;
import io.spring.start.repository.search.ChatSessionSearchRepository;
import io.spring.start.web.rest.util.HeaderUtil;
import io.spring.start.web.rest.util.PaginationUtil;
import io.spring.start.web.rest.vm.ChatSessionTrending;

/**
 * REST controller for managing ChatSession.
 */
@RestController
@RequestMapping("/api")
public class ChatSessionResource {

	private final Logger log = LoggerFactory.getLogger(ChatSessionResource.class);

	@Inject
	private ChatSessionRepository chatSessionRepository;

	@Inject
	private ChatSessionSearchRepository chatSessionSearchRepository;

	/**
	 * POST /chat-sessions : Create a new chatSession.
	 *
	 * @param chatSession
	 *            the chatSession to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new chatSession, or with status 400 (Bad Request) if the
	 *         chatSession has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/chat-sessions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ChatSession> createChatSession(@Valid @RequestBody ChatSession chatSession)
			throws URISyntaxException {
		log.debug("REST request to save ChatSession : {}", chatSession);
		if (chatSession.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chatSession", "idexists",
					"A new chatSession cannot already have an ID")).body(null);
		}
		ChatSession result = chatSessionRepository.save(chatSession);
		chatSessionSearchRepository.save(result);
		return ResponseEntity.created(new URI("/api/chat-sessions/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("chatSession", result.getId().toString())).body(result);
	}

	/**
	 * PUT /chat-sessions : Updates an existing chatSession.
	 *
	 * @param chatSession
	 *            the chatSession to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         chatSession, or with status 400 (Bad Request) if the chatSession
	 *         is not valid, or with status 500 (Internal Server Error) if the
	 *         chatSession couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/chat-sessions", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ChatSession> updateChatSession(@Valid @RequestBody ChatSession chatSession)
			throws URISyntaxException {
		log.debug("REST request to update ChatSession : {}", chatSession);
		if (chatSession.getId() == null) {
			return createChatSession(chatSession);
		}
		ChatSession result = chatSessionRepository.save(chatSession);
		chatSessionSearchRepository.save(result);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("chatSession", chatSession.getId().toString()))
				.body(result);
	}

	/**
	 * GET /chat-sessions : get all the chatSessions.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         chatSessions in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@RequestMapping(value = "/chat-sessions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<ChatSession>> getAllChatSessions(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of ChatSessions");
		Page<ChatSession> page = chatSessionRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chat-sessions");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /chat-sessions/:id : get the "id" chatSession.
	 *
	 * @param id
	 *            the id of the chatSession to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         chatSession, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/chat-sessions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ChatSession> getChatSession(@PathVariable Long id) {
		log.debug("REST request to get ChatSession : {}", id);
		ChatSession chatSession = chatSessionRepository.findOne(id);
		return Optional.ofNullable(chatSession).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /chat-sessions/:id : delete the "id" chatSession.
	 *
	 * @param id
	 *            the id of the chatSession to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/chat-sessions/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteChatSession(@PathVariable Long id) {
		log.debug("REST request to delete ChatSession : {}", id);
		chatSessionRepository.delete(id);
		chatSessionSearchRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chatSession", id.toString())).build();
	}

	/**
	 * SEARCH /_search/chat-sessions?query=:query : search for the chatSession
	 * corresponding to the query.
	 *
	 * @param query
	 *            the query of the chatSession search
	 * @param pageable
	 *            the pagination information
	 * @return the result of the search
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@RequestMapping(value = "/_search/chat-sessions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<ChatSession>> searchChatSessions(@RequestParam String query, Pageable pageable)
			throws URISyntaxException {
		log.debug("REST request to search for a page of ChatSessions for query {}", query);
		Page<ChatSession> page = chatSessionSearchRepository.search(queryStringQuery(query), pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
				"/api/_search/chat-sessions");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /chat-sessions : get all the chatSessions.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         chatSessions in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@RequestMapping(value = "/chat-sessions/trending", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<ChatSessionTrending>> getTrendingChatSessions(Pageable pageable)
			throws URISyntaxException {
		log.debug("REST request to get a page of ChatSessions");
		Page<ChatSession> page = chatSessionRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chat-sessions/trending");
		return new ResponseEntity<>(getTrendingCount(page.getContent()), headers, HttpStatus.OK);
	}

	private List<ChatSessionTrending> getTrendingCount(List<ChatSession> chatSessions) {
		Map<String, Long> map = new HashMap<String, Long>();
		chatSessions.forEach(session -> {
			if (map.containsKey(session.getTopic()) && session.isActiveStatus()) {
				map.put(session.getTopic(), map.get(session.getTopic()) + 1);
			} else {
				map.put(session.getTopic(), 1L);
			}
		});
		List<ChatSessionTrending> list = new ArrayList<>();
		for(Map.Entry<String, Long> entry : map.entrySet()){
			ChatSessionTrending chatSessionTrending = new ChatSessionTrending();
			chatSessionTrending.setTopic(entry.getKey());
			chatSessionTrending.setCount(entry.getValue());
			list.add(chatSessionTrending);
		}
		return list;
	}

}
