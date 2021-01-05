{:ok, sessionkey} = File.read("input/jason")
#IO.inspect sessionkey

url = "https://adventofcode.com/2020/day/12/input"
Application.ensure_all_started(:inets)
Application.ensure_all_started(:ssl)

{:ok, {{'HTTP/1.1', 200, 'OK'}, _headers, body}} =
  :httpc.request(:get, {url, [{'Cookie', to_charlist("session="<>sessionkey)}]}, [], [])

sample='
F10
N3
F7
R90
F11
'

lines = String.split(List.to_string(body), "\n", trim: true)


defmodule Navigator do

  def to_rad(degrees) do
    :math.pi() * degrees / 180.0
  end

  def rotate({x,y}, degrees) do
    x_new = Float.round(x * :math.cos(to_rad(degrees)) - y*:math.sin(to_rad(degrees)))
    y_new = Float.round(x * :math.sin(to_rad(degrees)) + y*:math.cos(to_rad(degrees)))
    #x′=xcos(θ)−ysin(θ)
    #y′=xsin(θ)+ycos(θ)
    {x_new, y_new}
  end

  def wayfare({x,y}, {wx,wy}, instructions) do
    if instructions == [] do
      abs(x) + abs(y)
    else
      [next | remaining] = instructions
      [command, amount] = parse_inst(next)
      IO.inspect("#{command}#{amount} #{x} #{y} | #{wx} #{wy}")
      case command do
        "N" -> wayfare({x,y}, {wx, wy + amount}, remaining)
        "E" -> wayfare({x,y}, {wx + amount, wy}, remaining)
        "S" -> wayfare({x,y}, {wx, wy - amount}, remaining)
        "W" -> wayfare({x,y}, {wx - amount, wy}, remaining)
        "L" -> wayfare({x, y}, rotate({wx,wy}, amount), remaining)
        "R" -> wayfare({x, y}, rotate({wx,wy}, -1 * amount), remaining)
        "F" -> wayfare({x+wx*amount, y+wy*amount}, {wx, wy}, remaining)
      end
    end
  end

  def navigate({x,y,d}, instructions) do
    if instructions == [] do
      abs(x) + abs(y)
    else
      [next | remaining] = instructions
      [command, amount] = parse_inst(next)
      IO.inspect("#{command}#{amount} #{x} #{y}")
      case command do
        "N" -> navigate({x, y + amount, d}, remaining)
        "E" -> navigate({x + amount, y, d}, remaining)
        "S" -> navigate({x, y - amount, d}, remaining)
        "W" -> navigate({x - amount, y, d}, remaining)
        "L" -> navigate({x, y, turn(command, d, amount)}, remaining)
        "R" -> navigate({x, y, turn(command, d, amount)}, remaining)
        "F" -> navigate({x, y, d}, ["#{d}#{amount}" | remaining])
      end
    end
  end

  def turn(cmd, d, amount) do
    directions = ["N", "E", "S", "W"]
    d_idx = Enum.find_index(directions, &(&1 == d))
    if(amount == 0) do
      d
    else
      case cmd do
        "L" -> turn(cmd, Enum.at(directions, rem(d_idx - 1, 4)), amount-90)
        "R" -> turn(cmd, Enum.at(directions, rem(d_idx + 1, 4)), amount-90)
      end
    end
  end

  def parse_inst(text) do
    direction = String.slice(text, 0, 1)
    amount = String.slice(text, 1, String.length(text))
    [direction, String.to_integer(amount)]
  end
end

#IO.inspect Navigator.parse_inst("E12")
IO.inspect(lines)
#IO.inspect Navigator.navigate({0,0, "E"}, lines)
#IO.inspect(Navigator.rotate( {3, 1}, 90))
IO.inspect Navigator.wayfare({0,0}, {10,1}, lines)
