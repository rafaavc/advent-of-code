use regex::Regex;
use std::collections::HashMap;

fn contains(bags: &HashMap<String, Vec<(String, u32)>>, haystack: &str, needle: &str) -> bool {
    for b in &bags[haystack] {
        if b.0 == needle || contains(bags, &b.0, needle) {
            return true;
        }
    }
    return false;
}

fn count(bags: &HashMap<String, Vec<(String, u32)>>, bag: &str) -> u32 {
    let mut c = 0;
    for b in &bags[bag] {
        c+= b.1*(count(bags, &b.0) + 1);
    }
    return c;
}

fn main() {
    let mut bags: HashMap<String, Vec<(String, u32)>> = HashMap::new();

    loop{
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();
        line.pop();
        if line.is_empty() {
            break;
        } else {
            let re = Regex::new(r"^([a-zA-Z ]+) bags contain ([0-9a-zA-Z, ]+)|no other bags.$").unwrap();
            let cap = re.captures(&line).unwrap();
            let key : String = String::from(&cap[1]); 
            let contents : String = String::from(&cap[2]);

            let re1 = Regex::new(r"([0-9]+) ([a-zA-Z ]+) bags?(, )?").unwrap();
            let cap1 = re1.captures_iter(&contents);
            let mut c : Vec<(String, u32)> = Vec::new();
            for i in cap1 {
                c.push((String::from(&i[2]), i[1].parse::<u32>().unwrap()));
            }

            bags.insert(key, c);
        }
    }
    
    println!("{}", count(&bags, "shiny gold"));
}
