#!/usr/bin/env ruby
# frozen_string_literal: true

require 'slop'
require 'json'
require 'rest-client'

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
RestClient.delete "#{opts[:baseUrl]}/api/pm", { Authorization: 'Bearer ...'}

puts 'done.'

# Create Address
puts 'Testcase : Creating Address'
json_test_data = JSON.generate({ 'streetName' => 'Hackersdum',  'streetNumber' =>  '007', 'postalCode' => '8306'  })
puts json_test_data
result = RestClient.post "#{opts[:baseUrl]}/api/pm/address", json_test_data,
                         { content_type: :json, accept: :json, Authorization: 'Bearer ...'}
puts result

# Create Address
puts 'Testcase : Creating another Address'
json_test_data = JSON.generate({ 'streetName' => 'Wyacher',  'streetNumber' =>  '1', 'postalCode' => '8400'  })
puts json_test_data
result = RestClient.post "#{opts[:baseUrl]}/api/pm/address", json_test_data,
                         { content_type: :json, accept: :json, Authorization: 'Bearer ...'}
puts result


# List all Adresses
puts 'Testcase : Listing all Addresses'
result = RestClient.get "#{opts[:baseUrl]}/api/pm/address",
                         { content_type: :json, accept: :json, Authorization: 'Bearer ...'}
puts result

puts 'All Testcases done.'


