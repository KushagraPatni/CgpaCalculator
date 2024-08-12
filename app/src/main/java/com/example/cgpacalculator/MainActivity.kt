package com.example.cgpacalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cgpacalculator.ui.theme.CgpaCalculatorTheme

data class Semester(val grade:String, val credit:Int?)
class MainActivity : ComponentActivity() {
    private var semesters:MutableList<Semester> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CgpaCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CGPA(semesters)
                }
            }
        }
    }
}
@Composable

fun CGPA(semesters: MutableList<Semester>){
    var grade1 by remember { mutableStateOf( "")}
    var credit1 by remember { mutableStateOf<Int?>( null)}
    var grade2 by remember { mutableStateOf( "")}
    var credit2 by remember { mutableStateOf<Int?>( null)}
    var grade3 by remember { mutableStateOf( "")}
    var credit3 by remember { mutableStateOf<Int?>( null)}
    var grade4 by remember { mutableStateOf( "")}
    var credit4 by remember { mutableStateOf<Int?>( null)}
    var cgpa by remember { mutableStateOf(0.0)    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        Text(text = "CGPA Calculator\n Padhle bhai CG dekhne se nhi badhegi",modifier=Modifier.fillMaxWidth(),

            style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center,
                color = Color(0xFF000000)
            )
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        subjectText(subject = "Subject1")
        gradeTextField(grade1){grade1=it}
        Spacer8dp()
        creditTextField(credit1) {credit1=it}
        Spacer8dp()
        subjectText(subject = "Subject2")
        gradeTextField(grade2){grade2=it}
        Spacer8dp()
        creditTextField(credit2) {credit2=it}
        Spacer8dp()
        subjectText(subject = "Subject3")
        gradeTextField(grade3){grade3=it}
        Spacer8dp()
        creditTextField(credit3) {credit3=it}
        Spacer8dp()
        subjectText(subject = "Subject4")
        gradeTextField(grade4){grade4=it}
        Spacer8dp()
        creditTextField(credit4) {credit4=it}
        Spacer8dp()
        Row {
            Column(modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    val semester1 = Semester(grade1, credit1 ?: 0)
                    val semester2 = Semester(grade2, credit2 ?: 0)
                    val semester3 = Semester(grade3, credit3 ?: 0)
                    val semester4 = Semester(grade4, credit4 ?: 0)

                    semesters.add(semester1)
                    semesters.add(semester2)
                    semesters.add(semester3)
                    semesters.add(semester4)

                    val totalCredit = semesters.sumOf { it.credit ?: 0 }
                    val totalGradePoints = semesters.sumOf { calculateGradePoints(it.grade, it.credit ?: 0) }

                    cgpa = if (totalCredit > 0) {
                        totalGradePoints / totalCredit.toDouble()
                    } else {
                        0.0
                    }

                    // Reset input fields
                    grade1 = ""
                    credit1 = null
                    grade2 = ""
                    credit2 = null
                    grade3 = ""
                    credit3 = null
                    grade4 = ""
                    credit4 = null
                }, colors = ButtonDefaults.buttonColors(
                    Color(0xFFBEABE0)
                ), shape = RoundedCornerShape(15.dp)) {
                    Text(text = "Calculate CGPA", fontSize = 16.sp, color = Color.Black)
                }

                Surface(modifier = Modifier
                .width(175.dp)
                .wrapContentHeight(),color=Color(0xFF263238), shape = RoundedCornerShape(15.dp)) {
                Text(
                    modifier = Modifier.padding(start=10.dp),
                    text="Overall\nCGPA: $cgpa",style=TextStyle(
                    fontSize = 16.sp,
                    color= Color(0xFFFFFFFF),

                ))
            }

            }
            Surface(modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp),color=Color(0xFF263238), shape = RoundedCornerShape(15.dp)) {
                Column {
                Text(
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                    text="Previous Sem\nCGPA:",style=TextStyle(
                        fontSize = 16.sp,
                        color= Color(0xFFFFFFFF),

                        ))
                if(semesters.isNotEmpty()) {
                    for(semester in semesters){
                    Text(
                        text = "Grade:${semester.grade} ,Credit:${semester.credit}",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }}
                }
            }
        }
    }

}

fun calculateGradePoints(grade: String, credit: Int) : Double {

return    when (grade.uppercase()) {
        "A"->10.0
        "B"->8.0
        "C"->6.0
        "D"->4.0
        else -> 0.0
    } * credit
}

@Composable
fun Spacer8dp(){
    Spacer(modifier = Modifier.padding(top = 8.dp))
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun gradeTextField(grade:String,onValueChange:(String)->Unit){
    TextField(value = grade, onValueChange ={text->onValueChange(text)},
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp), label = { Text(text = "Enter Grade",color=Color.White, fontSize = 12.sp)},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color(0xFF7E57C2),
        ),shape= RoundedCornerShape(15.dp),
        textStyle = TextStyle(fontSize = 12.sp, color= Color.White)
    )
}@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun subjectText(subject:String){

    Text(text = subject,modifier=Modifier.fillMaxWidth(),

        style = TextStyle(fontSize = 16.sp,
            color = Color(0xFF000000)
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun creditTextField(credit: Int?, onValueChange:(Int?)->Unit){
    TextField(value = credit?.toString() ?:"", onValueChange ={text->onValueChange(text.toIntOrNull())},
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp), label = { Text(text = "Enter Credit",color=Color.Black, fontSize = 12.sp)},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color(0xFF7D8CCED),
        ),shape= RoundedCornerShape(15.dp),
        textStyle = TextStyle(fontSize = 12.sp, color= Color.Black)
    )
}
@Preview(showBackground = true)
@Composable
fun CgpaPreview() {
    CgpaCalculatorTheme {
        CGPA(semesters = mutableListOf())
    }
}

//@Preview(showBackground = true)
//@Composable
//fun gradeTextField(){
//    CgpaCalculatorTheme {
//        gradeTextField()
//    }
//}


