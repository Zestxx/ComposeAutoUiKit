package com.zest.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zest.autouikit.core.annotation.DesignComponent
import com.zest.uikit.ComponentWithPreviewProvider.MultiTypeCard
import com.zest.uikit.ComponentWithPreviewProvider.MultiTypeData
import com.zest.uikit.ComponentWithPreviewProvider.UserCard
import com.zest.uikit.ComponentWithPreviewProvider.UserCardData

object TestObject {
    @DesignComponent(group = "Test in object")
    @Composable
    private fun TextUiComponentPreview() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
        ) {
            Text(modifier = Modifier.align(Alignment.Center), text = "Test sastext")
        }
    }


    @DesignComponent(group = "Test in object")
    @Composable
    private fun ButtonUiComponentPreview() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
        ) {
            Button(modifier = Modifier.align(Alignment.Center), onClick = {}) {
                Text(text = "Button")
            }
        }
    }

}

class TestClass {
    @DesignComponent(group = "Test in class")
    @Composable
    private fun TextUiComponentPreview() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
        ) {
            Text(modifier = Modifier.align(Alignment.Center), text = "Test sastext")
        }
    }


    @DesignComponent(group = "Test in class")
    @Composable
    private fun ButtonUiComponentPreview() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
        ) {
            Button(modifier = Modifier.align(Alignment.Center), onClick = {}) {
                Text(text = "Button")
            }
        }
    }

}

object ComponentWithPreviewProvider {
    data class UserCardData(
        val name: String,
        val status: String,
        val backgroundColor: Color
    )

    abstract class MultiTypeData(
        val name: String,
        val backgroundColor: Color
    ) {
        data object FirstType : MultiTypeData("First", Color.Blue)
        data object SecondType : MultiTypeData("Second", Color.Green)

    }

    @Composable
    fun UserCard(user: UserCardData) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(user.backgroundColor)
                .padding(16.dp)
        ) {
            Text(text = user.name, fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.status, fontSize = 14.sp, color = Color.White)
        }
    }

    @Composable
    fun MultiTypeCard(data: MultiTypeData) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(data.backgroundColor)
                .padding(16.dp)
        ) {
            Text(text = data.name, fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@DesignComponent(name = "Test with PreviewProvider")
@Preview()
@Composable
fun UserCardPreview(
    @PreviewParameter(UserCardPreviewProvider::class) user: UserCardData
) {
    UserCard(user = user)
}

class UserCardPreviewProvider : PreviewParameterProvider<UserCardData> {
    override val values = sequenceOf(
        UserCardData("Алексей", "Онлайн", Color(0xFF4CAF50)),
        UserCardData("Мария", "Не беспокоить", Color(0xFFF44336)),
        UserCardData("Иван", "Отошёл", Color(0xFF2196F3))
    )
}

@DesignComponent(name = "Test with MultiTypeData")
@Preview()
@Composable
fun MultiCardPreview(
    @PreviewParameter(MultiCardPreviewProvider::class) data: MultiTypeData
) {
    MultiTypeCard(data = data)
}

class MultiCardPreviewProvider : PreviewParameterProvider<MultiTypeData> {
    override val values = sequenceOf(
        MultiTypeData.FirstType,
        MultiTypeData.SecondType
    )
}

