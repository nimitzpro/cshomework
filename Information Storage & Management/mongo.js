// 1.
db.students.insert([
    {name:"Smith", student_number:17, class:1, major:"CS", grade_reports:[
        {section_identifier:112, grade:"B"}, 
        {section_identifier:119, grade:"C"}
    ]}, 

    {name:"Brown", student_number:8, class:1, major:"CS", grade_reports:[
        {section_identifier:85, grade:"A"}, 
        {section_identifier:92, grade:"A"}, 
        {section_identifier:102, grade:"B"}, 
        {section_identifier:135, grade:"A"}
    ]}
])

db.courses.insert([
    {course_name:"Intro to Computer Science", course_number:"CS1310", credit_hours:4, department:"CS", sections:[
        {section_identifier:92, course_number:"CS1310", semester:"Fall", year:"07", instructor:"Anderson"}, 
        {section_identifier:119, course_number:"CS1310", semester:"Fall", year:"08", instructor:"Anderson"}
    ]}, 

    {course_name:"Data Structures", course_number:"CS3320", credit_hours:4, department:"CS", prerequisites:["CS1310"], sections:[
        {section_identifier:102, course_number:"CS3320", semester:"Spring", year:"08", instructor:"Knuth"}
    ]}, 

    {course_name:"Discrete Mathematics", course_number:"MATH2410", credit_hours:3, department:"MATH", sections:[
        {section_identifier:85, semester:"Fall", year:"07", instructor:"King"}, 
        {section_identifier:112, semester:"Fall", year:"08", instructor:"Chang"}
    ]}, 

    {course_name:"Database", course_number:"CS3380", credit_hours:3, department:"CS", prerequisites:["CS3320", "MATH2410"], sections:[
        {section_identifier:135, semester:"Fall", year:"08", instructor:"Stone"}
    ]}
])

// 2.
db.students.aggregate([
    {$match:{name:"Smith"}},
    {
    $lookup:{
        from:"courses",
        localField: "grade_reports.section_identifier",
        foreignField:"sections.section_identifier",
        as: "courses"
    }
}
]).pretty()

// 3.
db.courses.aggregate([
    {$match:{course_name:"Database", sections:{$elemMatch:{semester:"Fall", year:"08"}}}},
    {
        $lookup:{
            from:"students",
            let: {sec: "$sections.section_identifier"},
            pipeline:[
                {$unwind:"$grade_reports"},
                { $match:
                    { $expr:{
                        $in: ["$grade_reports.section_identifier","$$sec"]
                        }
                    }
                }
            ],
            as: "students"
        },
        },
        {$unwind: "$students"},
        {$project:{
            _id:0, name: "$students.name", 
            grade:"$students.grade_reports.grade"
        }
    }
]).pretty()

// 4.
db.courses.aggregate([
    {$match:{course_name:"Database"}},
    {
        $lookup:{
            from:"courses",
            let: {prerequisites: "$prerequisites"},
            pipeline: [
                {
                    $match: {
                        $expr:{
                            $in: ["$course_number", "$$prerequisites"]
                        }
                    }
                }
            ],
            as: 'required_courses'
        }
    },
    {
        $unwind: "$required_courses"
    },
    {
        $project:{
            course_name: "$required_courses.course_name",
            _id: 0
        }
    }
]).pretty()

db.db.insert([
    { "_id": 1, "name" : "Peter", "hobbies" :[ 'steep', "movies" ], "customers" : ["Tesco", "Aldi", "Centra" ]},
    { "_id": 2, "name" : "Alex", "hobbies" :[ "dance", "movies" ], "customers" : [ "Tesco", "Aldi" ]},
    { "_id": 3, "name" : "Barry", "hobbies" :[ 'travel', "movies"], "customers" : []},
    { "_id": 4, "name" : "Sean", "hobbies" :[ "dance", "hiking", "movies" ], "customers" :[ "Centra" ]}
])

db.db.aggregate([ {$unwind: "$hobbies"}, {$unwind: "$customers"}, {$group: {_id: "$hobbies", total: {$sum: 1}} } ])

{ "_id" : "dance", "total" : 3 }
{ "_id" : "hiking", "total" : 1 }
{ "_id" : "steep", "total" : 3 }
{ "_id" : "movies", "total" : 6 }

db.db.insert([
    {"_id":1, "name" : "Alex", "m": "April", "distKm" : 5, "tMin": 33 },
    {"_id":2, "name": "Alex", "m": "March", "distKm": 2, "tMin": 12}
])
