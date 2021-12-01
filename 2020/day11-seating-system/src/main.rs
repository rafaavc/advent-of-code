fn get_n_adjacent_occupied(seats: Vec<String>, line_idx: usize, seat_idx: usize) -> usize {
    let mut res = 0;
    let prev_seat_idx = seat_idx as i32 - 1;
    let mut next_seat_idx = -1;

    if seat_idx < seats[1].len() - 1 {
        next_seat_idx = seat_idx as i32 + 1;
    }


    if prev_seat_idx != -1 && seats[line_idx].chars().nth(prev_seat_idx as usize).unwrap() == '#' {
        res += 1;
    }
    if next_seat_idx != -1 && seats[line_idx].chars().nth(next_seat_idx as usize).unwrap() == '#' {
        res += 1;
    }
    
    if line_idx > 0 {
        let prev_line_idx = line_idx + 1;
        
        if seats[prev_line_idx].chars().nth(seat_idx).unwrap() == '#' {
            res += 1;
        }
        if prev_seat_idx != -1 && seats[prev_line_idx].chars().nth(prev_seat_idx as usize).unwrap() == '#' {
            res += 1;
        }
        if next_seat_idx != -1 && seats[prev_line_idx].chars().nth(next_seat_idx as usize).unwrap() == '#' {
            res += 1;
        }
    }

    if line_idx < seats.len() - 1 {
        let next_line_idx = line_idx + 1;
        
        if seats[next_line_idx].chars().nth(seat_idx).unwrap() == '#' {
            res += 1;
        }
        if prev_seat_idx != -1 && seats[next_line_idx].chars().nth(prev_seat_idx as usize).unwrap() == '#' {
            res += 1;
        }
        if next_seat_idx != -1 && seats[next_line_idx].chars().nth(next_seat_idx as usize).unwrap() == '#' {
            res += 1;
        }
    }
    return res;
}

fn main() {
    let mut seats: Vec<String> = Vec::new();
    loop {
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();

        line.pop();
        if line.is_empty() {
            break;
        } else {
            seats.push(line);
        }
    }

    loop {
        let mut changed = false;
        for (line_idx, line) in seats.iter().enumerate() {
            for (char_idx, mut seat) in line.chars().enumerate() {
                if seat == '.' {
                    continue;
                }
                let n_adjacent_occupied = get_n_adjacent_occupied(seats, line_idx, char_idx);
                if seat == 'L' && n_adjacent_occupied == 0 {
                    changed = true;
                    seat = '#';
                } else if seat == '#' && n_adjacent_occupied >= 4 {
                    changed = true;
                    seat = 'L';
                }
            }
        }
        if !changed {
            break;
        }
    }

    let c = -1;
    println!("RES = {}", c);
}