SELECT `row`,
		gh_project_name,
		tr_build_number,
		tr_status,
		tr_tests_failed,
		tr_duration,
		tr_testduration,
		gh_test_lines_per_kloc,
		gh_test_cases_per_kloc,
		gh_tests_added,
		gh_test_churn,
		gh_src_churn
FROM travistorrent_27_10_2016
WHERE tr_status='passed'
	AND tr_tests_failed=true
INTO OUTFILE '/tmp/msr.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';