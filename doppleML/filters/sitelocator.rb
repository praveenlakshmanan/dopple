# Call this file 'sitelocator.rb' (in logstash/filters, as above)
require "logstash/filters/base"
require "logstash/namespace"
require "csv"

class LogStash::Filters::Sitelocator < LogStash::Filters::Base

  # Setting the config_name here is required. This is how you
  # configure this filter from your logstash config.
  #
  # filter {
  #   sitelocator {sitename => "field" , datafile=>path to csv file}
  # }
  config_name "sitelocator"

  # New plugins should start life at milestone 1.
  milestone 1

  # Defines the site name
   config :sitename, :validate => :string , :default=>"N/A"
  # path of the CSV
   config :datafile, :validate => :string

  public
  def register
    # initialize
	location_code = 0	
	location_pos = 1
	city_pos = 2
	lat_pos = 3
	lon_pos = 4
	@lookup = Hash.new
         if !File.exists?(@datafile)
        	raise "You must specify 'datafile => ...' in your sitelocator filter (I looked for '#{@csvfile}'"
        else
	CSV.foreach(@datafile,{:col_sep =>";", :headers => true}) do |row|
		site = Hash.new	
		
		site["city"] = row[city_pos]
		site["location"] = row[location_pos]
		site["latitude"] = row[lat_pos]
		site["longitude"] = row[lon_pos]
		@lookup[row[0].downcase] = site
	end
	end
  end # def register

  public
  def filter(event)
    # return nothing unless there's an actual filter event
    return unless filter?(event)
   #read form csv later
 if !@sitename.nil? || !@sitename.empty?
   event["_location"] = @lookup[event[@sitename]]["location"]
   event["city"] = @lookup[event[@sitename]]["city"] 
   event["coordinates"] =  [@lookup[event[@sitename]]["longitude"].to_f, @lookup[event[@sitename]]["latitude"].to_f ]
end
 #   if event[@sitename].eql?"ROW"
	
#	event["_location"] = "Site T"
#    elsif event[@sitename].eql?"L"
#	event["_location"]="Site L"
#    elsif event[@sitename].eql?"S"
#	event["_location"]="Site S"
 #   end	
    # filter_matched should go in the last line of our successful code 
    filter_matched(event)
  end # def filter
end # class LogStash::Filters::Foo

