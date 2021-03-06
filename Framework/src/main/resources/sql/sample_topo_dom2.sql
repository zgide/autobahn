INSERT INTO node VALUES (1, NULL, NULL, 'Node2.1', NULL, NULL, NULL, NULL, NULL, NULL, 'Node2.1', false);
INSERT INTO node VALUES (2, NULL, NULL, 'Node2.2', NULL, NULL, NULL, NULL, NULL, NULL, 'Node2.2', false);
INSERT INTO node VALUES (3, NULL, NULL, 'DOM1-node-1', NULL, NULL, NULL, NULL, NULL, NULL, 'DOM1-node-1', false);
INSERT INTO node VALUES (4, NULL, NULL, 'DOM1-node-2', NULL, NULL, NULL, NULL, NULL, NULL, 'DOM1-node-2', false);
INSERT INTO node VALUES (5, NULL, NULL, 'host-node', NULL, NULL, NULL, NULL, NULL, NULL, 'host-node', false);


INSERT INTO generic_interface VALUES (1, NULL, NULL, NULL, 1, 'p2.1', NULL, 1000000000, NULL, 0, 'http://150.254.160.216:8081/autobahn/interdomain', false);
INSERT INTO generic_interface VALUES (2, NULL, NULL, NULL, 1, 'p2.2', NULL, 1000000000, NULL, 0, 'http://150.254.160.216:8081/autobahn/interdomain', false);
INSERT INTO generic_interface VALUES (3, NULL, NULL, NULL, 2, 'p2.3', NULL, 10000000000, NULL, 0, 'http://150.254.160.216:8081/autobahn/interdomain', false);
INSERT INTO generic_interface VALUES (4, NULL, NULL, NULL, 1, 'p2.4', NULL, 10000000000, NULL, 0, NULL, false);
INSERT INTO generic_interface VALUES (5, NULL, NULL, NULL, 2, 'p2.5', NULL, 10000000000, NULL, 0, NULL, false);
INSERT INTO generic_interface VALUES (6, NULL, NULL, NULL, 3, 'DOM1-port-1', NULL, 1000000000, NULL, 0, 'http://150.254.160.216:8080/autobahn/interdomain', false);
INSERT INTO generic_interface VALUES (7, NULL, NULL, NULL, 4, 'DOM1-port-2', NULL, 1000000000, NULL, 0, 'http://150.254.160.216:8080/autobahn/interdomain', false);
INSERT INTO generic_interface VALUES (8, NULL, NULL, NULL, 5, 'host-port', 'A host port of clientDom attached to dom2', 10000000000, NULL, 0, 'http://client-domain.domain2.com', true);


INSERT INTO generic_link VALUES (1, NULL, 1, 6, NULL, false, 0);
INSERT INTO generic_link VALUES (2, NULL, 2, 7, NULL, false, 0);
INSERT INTO generic_link VALUES (3, NULL, 3, 8, NULL, false, 0);
INSERT INTO generic_link VALUES (4, NULL, 4, 5, NULL, false, 0);


INSERT INTO eth_link VALUES (1, NULL, false, false, 0);
INSERT INTO eth_link VALUES (2, NULL, false, false, 0);
INSERT INTO eth_link VALUES (3, NULL, false, false, 0);
INSERT INTO eth_link VALUES (4, NULL, false, false, 0);


INSERT INTO vlan VALUES (1, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (2, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (3, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (4, NULL, NULL, 100, 200);


INSERT INTO spanning_tree VALUES (1, 1, NULL, 0);
INSERT INTO spanning_tree VALUES (2, 2, NULL, 0);
INSERT INTO spanning_tree VALUES (3, 3, NULL, 0);
INSERT INTO spanning_tree VALUES (4, 4, NULL, 0);
