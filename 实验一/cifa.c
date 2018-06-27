#include<stdio.h>
#include<stdlib.h>
#include<string.h>

typedef struct Word
{
    char type[50];//词所属类型
    char w[20];//词
} Word;

int main()
{
    char ch,word_temp[20]="";
    int i=0,j=0,key=0;
    char ktt[38][20]= {"int","char","void","if","else","switch","case","default","while","do","for","break","continue","return",
                       "+","-","*","/","%","++","--","!","&&","||","=",">",">=","<","<=","==","!=",
                       ",",":",";","(",")","{","}"
                      };
    char ktt1[38][20]= {"kw_int","kw_char","kw_void","kw_if","kw_else","kw_switch","kw_case","kw_default","kw_while","kw_do","kw_for","kw_break","kw_continue","kw_return",
                        "add","sub","mul","div","mod","inc","dec","not","and","or","assign","gt","ge","lt","le","equ","nequ",
                        "comma","colon","simcon","lparen","rparen","lbrac","rbrac"
                       };
    FILE *fp;
    Word word[500];
    fp = fopen("D:/1.c","r");
    if(!fp)
    {
        printf("can't open file\n");
        exit(1);
    }
    while((ch=fgetc(fp))!= EOF)
    {
        putchar(ch);
        if((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')||(ch>='0'&&ch<='9'))
        {
            word_temp[key]=ch;//连续几个字母的连成单词
            word_temp[key+1]='\0';
            key++;
            continue;
        }
        if(strcmp(word_temp,"")!=0)
        {
            strcpy(word[i].w,word_temp);//将单词拷贝到结构数组中
            strcpy(word_temp,"");
            key=0;//回到临时数组的开始位置
            i++;//结构数组的下标加1
            continue;
        }
        else if(ch==' '||ch==10||ch=='   ')   //去除空格，回车和tab
        {
            continue;
        }
        else
        {
            word_temp[0]=ch;
            word_temp[1]='\0';
            strcpy(word[i].w,word_temp);//将非字母数字符号拷贝到结构数组中
            strcpy(word_temp,"");
            key=0;//回到临时数组的开始位置
            i++;
        }
    }
    printf("\n词法分析结果如下:\n");
    int k = 0;
    for(j=0; j<i; j++)
    {
        for(k=0; k<38; k++)
        {
            if((strcmp(word[j].w,ktt[k]))==0)
            {
                if(k>=0&&k<14)
                    strcpy(word[j].type,ktt1[k]);//关键字
                else if(k>=14&&k<31)
                    strcpy(word[j].type,ktt1[k]);//运算符
                else if(k>=31&&k<38)
                    strcpy(word[j].type,ktt1[k]);//分界符
                break;
            }
            else if(word[j].w[0]>='0'&&word[j].w[0]<='9')
            {
                if(word[j].w[0] == '0')
                {
                    strcpy(word[j].type,"八进制数字");
                    if(word[j].w[1] == 'x')
                    {
                        strcpy(word[j].type,"十六进制数字");
                    }

                }
                else
                {
                    strcpy(word[j].type,"十进制数字");
                }
            }
            else if(strlen(word[j].w) == 1)
            {

                strcpy(word[j].type,"ch");//字符
            }
            else
            {
                strcpy(word[j].type,"str");//字符串
            }
        }
    }
    for(j=0; j<i; j++)
    {
        printf("(%s,'%s')\n",word[j].type,word[j].w);
    }

    fclose(fp);
    return 0;
}
