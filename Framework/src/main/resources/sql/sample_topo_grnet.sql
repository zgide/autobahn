INSERT INTO node VALUES (1, NULL, NULL, 'Node1.1', NULL, NULL, NULL, NULL, NULL, NULL, 'Node1.1', false);
INSERT INTO node VALUES (2, NULL, NULL, 'Node1.2', NULL, NULL, NULL, NULL, NULL, NULL, 'Node1.2', false);
INSERT INTO node VALUES (3, NULL, NULL, 'DOM2-node-1', NULL, NULL, NULL, NULL, NULL, NULL, 'DOM2-node-1', false);
INSERT INTO node VALUES (4, NULL, NULL, 'host-node', NULL, NULL, NULL, NULL, NULL, NULL, 'host-node', false);
INSERT INTO node VALUES (5, NULL, NULL, 'Node1.3', NULL, NULL, NULL, NULL, NULL, NULL, 'Node1.3', false);
INSERT INTO node VALUES (6, NULL, NULL, 'DOM3-node-1', NULL, NULL, NULL, NULL, NULL, NULL, 'DOM3-node-1', false);


INSERT INTO generic_interface VALUES (1, NULL, NULL, NULL, 1, 'p1.1', NULL, 1000000000, NULL, 0, 'GRNET', false);
INSERT INTO generic_interface VALUES (2, NULL, NULL, NULL, 1, 'p1.2', NULL, 1000000000, NULL, 0, 'GRNET', false);
INSERT INTO generic_interface VALUES (3, NULL, NULL, NULL, 2, 'p1.3', NULL, 10000000000, NULL, 0, 'GRNET', false);
INSERT INTO generic_interface VALUES (4, NULL, NULL, NULL, 1, 'p1.4', NULL, 1000000000, NULL, 0, NULL, false);
INSERT INTO generic_interface VALUES (5, NULL, NULL, NULL, 2, 'p1.5', NULL, 1000000000, NULL, 0, NULL, false);
INSERT INTO generic_interface VALUES (6, NULL, NULL, NULL, 3, 'DOM2-port-1', NULL, 1000000000, NULL, 0, 'PSNC', false);
INSERT INTO generic_interface VALUES (7, NULL, NULL, NULL, 3, 'DOM2-port-2', NULL, 1000000000, NULL, 0, 'PSNC', false);
INSERT INTO generic_interface VALUES (8, NULL, NULL, NULL, 4, 'host-port', 'A host port of GRNET client domain', 10000000000, NULL, 0, 'http://client-domain.grnet.gr', true);
INSERT INTO generic_interface VALUES (9, NULL, NULL, NULL, 2, 'p1.6', NULL, 1000000000, NULL, 0, NULL, false);
INSERT INTO generic_interface VALUES (10, NULL, NULL, NULL, 5, 'p1.7', NULL, 1000000000, NULL, 0, NULL, false);
INSERT INTO generic_interface VALUES (11, NULL, NULL, NULL, 5, 'p1.8', NULL, 1000000000, NULL, 0, 'GRNET', false);
INSERT INTO generic_interface VALUES (12, NULL, NULL, NULL, 6, 'DOM3-port-1', NULL, 1000000000, NULL, 0, 'NORDUNET', false);


INSERT INTO generic_link VALUES (1, NULL, 1, 6, NULL, false, 0);
INSERT INTO generic_link VALUES (2, NULL, 2, 7, NULL, false, 0);
INSERT INTO generic_link VALUES (3, NULL, 3, 8, NULL, false, 0);
INSERT INTO generic_link VALUES (4, NULL, 4, 5, NULL, false, 0);
INSERT INTO generic_link VALUES (5, NULL, 9, 10, NULL, false, 0);
INSERT INTO generic_link VALUES (6, NULL, 11, 12, NULL, false, 0);


INSERT INTO eth_link VALUES (1, NULL, false, false, 0);
INSERT INTO eth_link VALUES (2, NULL, false, false, 0);
INSERT INTO eth_link VALUES (3, NULL, false, false, 0);
INSERT INTO eth_link VALUES (4, NULL, false, false, 0);
INSERT INTO eth_link VALUES (5, NULL, false, false, 0);
INSERT INTO eth_link VALUES (6, NULL, false, false, 0);


INSERT INTO vlan VALUES (1, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (2, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (3, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (4, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (5, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (6, NULL, NULL, 100, 200);


INSERT INTO spanning_tree VALUES (1, 1, NULL, 0);
INSERT INTO spanning_tree VALUES (2, 2, NULL, 0);
INSERT INTO spanning_tree VALUES (3, 3, NULL, 0);
INSERT INTO spanning_tree VALUES (4, 4, NULL, 0);
INSERT INTO spanning_tree VALUES (5, 5, NULL, 0);
INSERT INTO spanning_tree VALUES (6, 6, NULL, 0);
