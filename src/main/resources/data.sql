insert into problems(description, input_description, output_description, memory, time, name, num_of_failed,
                     num_of_submits, num_of_success, created_at, updated_at)
values ('정수를 입력받고 해당 수 만큼 Hello World를 출력하시오.', '0~100 사이의 정수 n이 입력됩니다.', 'n수만큼 출력하시오.', 256, 1,
        'ouput exercise problem', 0, 0, 0, '2022-02-20 11:01:31', '2022-02-20 11:01:31');

insert into test_cases(problem_id, name, input_file_path, output_file_path, output_hash)
values (1, '1', '/problems/a14a8cff-8686-4240-82d1-3985b169eb8d/1.in',
        '/problems/a14a8cff-8686-4240-82d1-3985b169eb8d/1.out', '[B@5c8d7310');

insert into test_cases(problem_id, name, input_file_path, output_file_path, output_hash)
values (1, '2', '/problems/a14a8cff-8686-4240-82d1-3985b169eb8d/2.in',
        '/problems/a14a8cff-8686-4240-82d1-3985b169eb8d/2.out', '[B@50ae870b');

