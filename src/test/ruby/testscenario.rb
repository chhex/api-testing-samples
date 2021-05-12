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

def retrieve_all_pretty(opts)
  result = RestClient.get "#{opts[:baseUrl]}/api/testdata"
  appData = JSON.parse(result.body)
  puts JSON.pretty_generate(appData) if opts[:verbose] and !appData.length.zero?
  appData.length()
end
# Delete All
RestClient.delete "#{opts[:baseUrl]}/api/testdata"
nr = retrieve_all_pretty(opts)
raise "Expected zero Testdata entries , but got #{nr}" unless nr.zero?

# Create First Data
RestClient.post "#{opts[:baseUrl]}/api/testdata",
                JSON.pretty_generate(
                  {
                    'text' => "Some Data at #{Time.now}"
                  }
                ), { content_type: :json, accept: :json }
nr = retrieve_all_pretty(opts)
raise "Expected one Testdata entries , but got #{nr}" unless nr == 1

# Create Some more Data
RestClient.post "#{opts[:baseUrl]}/api/testdata",
                JSON.pretty_generate(
                  {
                    'text' => "Some more Data at #{Time.now}"
                  }
                ), { content_type: :json, accept: :json }
nr = retrieve_all_pretty(opts)
raise "Expected one Testdata entries , but got #{nr}" unless nr == 2


