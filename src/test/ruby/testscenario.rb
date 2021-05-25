#!/usr/bin/env ruby
# frozen_string_literal: true

require 'slop'
require 'json'
require 'rest-client'
require 'json-compare'

def retrieve_all_pretty(opts)
  result = RestClient.get "#{opts[:baseUrl]}/api/testdata", { Authorization:'Bearer ...'}
  app_data = JSON.parse(result.body)
  puts JSON.pretty_generate(app_data) if opts[:verbose] && !app_data.length.zero?
  app_data.length
end

opts = Slop.parse do |o|
  o.string '-b', '--baseUrl', 'Url to post to, default: http://localhost:8080', default: 'http://localhost:8080'
  o.bool '-v', '--verbose', 'Print more information', default: false
  o.on '-h', '--help' do
    puts o
    exit
  end
end

# Main Line

# Delete All (Setup)
puts 'Setup : Deleting all data'
RestClient.delete "#{opts[:baseUrl]}/api/testdata", { Authorization: 'Bearer ...'}
nr = retrieve_all_pretty(opts)
raise "Expected zero Testdata entries , but got #{nr}" unless nr.zero?

puts 'done.'

# Create First Data
puts 'Testcase : Creating Test Data'
json_test_data = JSON.generate({ 'textData' => "Some Data at #{Time.now}" })
puts json_test_data
result = RestClient.post "#{opts[:baseUrl]}/api/testdata", json_test_data,
                         { content_type: :json, accept: :json, Authorization: 'Bearer ...'}
nr = retrieve_all_pretty(opts)
raise "Expected one Testdata entries , but got #{nr}" unless nr == 1

result_data = JSON.parse(result.body)
raise 'Expected id' if result_data['id'].nil?

puts 'done.'

# Create Some more Data
puts 'Testcase : Creating some more Test Data'
json_test_data = JSON.generate({ 'textData' => "Some more Data at #{Time.now}" })

RestClient.post "#{opts[:baseUrl]}/api/testdata", json_test_data,
                { content_type: :json, accept: :json, Authorization: 'Bearer ...'}
nr = retrieve_all_pretty(opts)
raise "Expected one Testdata entries , but got #{nr}" unless nr == 2

result_data = JSON.parse(result.body)
raise 'Expected id' if result_data['id'].nil?

puts 'done.'

# Find by Id Existing

puts 'Testcase : Finding Data by Id'
result = RestClient.get "#{opts[:baseUrl]}/api/testdata/#{result_data['id']}",
                        { content_type: :json, accept: :json, Authorization: 'Bearer ...'}
current_result_data = JSON.parse(result.body)
raise "Expected : <#{result_data}> equal to: <#{current_result_data}>" unless result_data == current_result_data

puts 'done.'

# Update by Id

json_test_data = JSON.generate({ 'id' => result_data['id'] , 
                                 'textData' => "#{current_result_data['textData']} Updated"})
result = RestClient.put "#{opts[:baseUrl]}/api/testdata/#{result_data['id']}", json_test_data,
                        { content_type: :json, accept: :json, Authorization: 'Bearer ...'}
diff = JsonCompare.get_diff(result.body, json_test_data)
raise "Expected : <#{result.body}> equal to: <#{json_test_data}>" if diff.size.positive?

# Calculate and update

puts 'Testcase : Calculation by id '
result = RestClient.put "#{opts[:baseUrl]}/api/testdata/#{result_data['id']}/2*2", nil,
                        { Authorization: 'Bearer ...'}
expected_result = 'Calculation Result of 2*2 is 4'
current_result_data = JSON.parse(result.body)
unless current_result_data['textData'] == expected_result
  raise "Expected : <#{current_result_data['textData']}> equal to: <#{expected_result}>"
end

puts 'done.'

# Delete by Id

puts 'Testcase : Deleting Data by Id'
RestClient.delete "#{opts[:baseUrl]}/api/testdata/#{result_data['id']}", { content_type: :json, accept: :json }
nr = retrieve_all_pretty(opts)
raise "Expected one Testdata entry , but got #{nr}" unless nr == 1

puts 'done.'

puts 'All Testcases done.'


