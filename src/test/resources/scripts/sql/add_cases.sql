insert into cases
(id, uuid, visible, case_genus, category, comment, content, status, contractual_verification_date, realization_date, creation_date_time)
values
(1, '3fb55ff0-0040-443e-9ad7-cf7f2682e8a1', true, 'MANUAL', 'TRAININGS', 'some_comment_1', 'some_content_1', 'REALIZED', '2031-06-01', null, ('2020-06-10 18:43:20')::timestamp);

insert into cases
(id, uuid, visible, case_genus, category, comment, content, status, contractual_verification_date, realization_date, creation_date_time)
values
(2, 'c61c2ce8-30c5-4e59-acf7-caf576f286bb', true, 'AUTOMATIC', 'EVENTS', 'some_comment_2', 'some_content_2', 'UNREALIZED', '2041-06-01', null, ('2020-06-10 18:43:20')::timestamp);